package com.example.pixbayphoto.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.usecase.GetItemsSortedByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemsSortedByIdUseCase: GetItemsSortedByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    private val _query = _state.map { it.query }
        .distinctUntilChanged()
        .debounce(700)

    init {
        fetchItems()

        viewModelScope.launch {
            _query.drop(1)
                .collect { query ->
                    fetchItems(query)
                }
        }
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnImageClick -> handleEvent(MainEvent.NavigateToDetail(action.id))
            is MainAction.OnSearchAction -> fetchItems(action.query)
            is MainAction.OnValueChange -> handleValueChange(action.query)
            is MainAction.OnRefresh -> fetchItems(action.query)
            is MainAction.OnRetry -> fetchItems(action.query)
        }
    }

    private fun fetchItems(query: String = "") {
        getItemsSortedByIdUseCase(query)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success<List<Item>> -> _state.update {
                        it.copy(
                            items = resource.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            error = resource.message,
                            isLoading = false
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleValueChange(query: String) {
        _state.update {
            it.copy(query = query)
        }
    }

    private fun handleEvent(event: MainEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}