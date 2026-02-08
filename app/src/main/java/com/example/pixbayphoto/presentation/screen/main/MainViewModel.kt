package com.example.pixbayphoto.presentation.screen.main

import androidx.lifecycle.ViewModel
import com.example.pixbayphoto.domain.usecase.GetItemsSortedByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemsSortedByIdUseCase: GetItemsSortedByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow();

    fun onAction(action: MainAction) {
        when (action) {
            else -> {}
        }
    }
}