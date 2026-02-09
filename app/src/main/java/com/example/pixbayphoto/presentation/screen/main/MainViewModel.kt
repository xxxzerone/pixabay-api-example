package com.example.pixbayphoto.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixbayphoto.core.fold
import com.example.pixbayphoto.domain.usecase.GetItemsSortedByIdUseCase
import com.example.pixbayphoto.presentation.screen.main.MainEvent.OnNavigateToDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemsSortedByIdUseCase: GetItemsSortedByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow();

    private val _uiEvent = MutableSharedFlow<MainEvent>();
    val uiEvent = _uiEvent.asSharedFlow();

    init {
        fetchItems(_uiState.value.query)
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnItemClick -> handleEvent(OnNavigateToDetail(action.id))
            is MainAction.OnValueChange -> handleSearchValueChange(action.query)
            is MainAction.OnSearchAction -> fetchItems(action.query)
        }
    }

    private fun fetchItems(query: String) {
        viewModelScope.launch {
            getItemsSortedByIdUseCase(query).collect { resource ->
                _uiState.update {
                    resource.fold(
                        onSuccess = { data -> it.copy(isLoading = false, items = data) },
                        onFailure = { message, _ -> it.copy(isLoading = false, error = message) },
                        onLoading = { it.copy(isLoading = true) }
                    )
                }
            }
        }
    }

    private fun handleSearchValueChange(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    private fun handleEvent(event: MainEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}