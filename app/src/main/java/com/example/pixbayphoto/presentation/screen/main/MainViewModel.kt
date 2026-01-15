package com.example.pixbayphoto.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.usecase.GetItemsSortedByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemsSortedByIdUseCase: GetItemsSortedByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        fetchItems()
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnImageClick -> TODO()
            is MainAction.OnSearchAction -> TODO()
            is MainAction.OnValueChange -> handleValueChange(action.query)
        }
    }

    private fun fetchItems() {
        getItemsSortedByIdUseCase()
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

}