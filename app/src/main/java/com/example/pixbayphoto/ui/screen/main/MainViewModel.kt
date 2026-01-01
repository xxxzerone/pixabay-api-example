package com.example.pixbayphoto.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixbayphoto.core.Result
import com.example.pixbayphoto.domain.repository.PixabayRepository
import com.example.pixbayphoto.ui.screen.main.MainEvent.NavigateToDetail
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MainViewModel(
    private val pixabayRepository: PixabayRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    private val _query = _state.map { it.query }
        .distinctUntilChanged()
        .debounce(500)

    init {
        fetchPixabay()

        viewModelScope.launch {
            _query.drop(1)
                .collect { query ->
                    fetchPixabay(query)
                }
        }
    }

    fun handleActon(action: MainAction) {
        when (action) {
            is MainAction.OnPhotoClick -> emitEvent(NavigateToDetail(action.id))
            is MainAction.OnValueChange -> handleValueChange(action.query)
            is MainAction.OnSearchAction -> fetchPixabay(action.query)
        }
    }

    private fun fetchPixabay(query: String = "") {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = pixabayRepository.loadPhoto(query = query)
            when (result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(pixabays = result.data, isLoading = false)
                    }
                }

                is Result.Failure -> {
                    _state.update {
                        it.copy(isLoading = false, error = "Error: ${result.message}")
                    }
                }
            }
        }
    }

    private fun handleValueChange(query: String) {
        _state.update {
            it.copy(query = query)
        }
    }

    private fun emitEvent(event: MainEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}