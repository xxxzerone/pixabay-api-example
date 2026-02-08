package com.example.pixbayphoto.presentation.screen.detail

import androidx.lifecycle.ViewModel
import com.example.pixbayphoto.domain.usecase.GetItemByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow();

}