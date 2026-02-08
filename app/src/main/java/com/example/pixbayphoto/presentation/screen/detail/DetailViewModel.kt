package com.example.pixbayphoto.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.pixbayphoto.core.fold
import com.example.pixbayphoto.domain.usecase.GetItemByIdUseCase
import com.example.pixbayphoto.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModel() {
    private val _detailRoute: Route.Detail = savedStateHandle.toRoute()
    private val _itemId = _detailRoute.itemId

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow();

    private val _uiEvent = MutableSharedFlow<DetailEvent>();
    val uiEvent = _uiEvent.asSharedFlow();

    init {
        fetchItem()
    }

    fun onAction(action: DetailAction) {
        when (action) {
            else -> {}
        }
    }

    private fun fetchItem() {
        viewModelScope.launch {
            getItemByIdUseCase(_itemId).collect { resource ->
                _uiState.update {
                    resource.fold(
                        onSuccess = { data -> it.copy(isLoading = false, item = data) },
                        onFailure = { message, _ -> it.copy(isLoading = false, error = message) },
                        onLoading = { it.copy(isLoading = true) }
                    )
                }
            }
        }
    }
}