package com.example.pixbayphoto.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixbayphoto.core.Result
import com.example.pixbayphoto.domain.repository.PixabayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val pixabayRepository: PixabayRepository,
    private val id: Int
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    init {
        fetchPixabay()
    }

    private fun fetchPixabay() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = pixabayRepository.loadPhoto(query = "")
            when (result) {
                is Result.Success -> {
                    val pixabay = result.data.firstOrNull { it.id == id }

                    _state.update {
                        it.copy(pixabay = pixabay, isLoading = false)
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
}