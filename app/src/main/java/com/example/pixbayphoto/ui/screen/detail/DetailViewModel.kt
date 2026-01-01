package com.example.pixbayphoto.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixbayphoto.core.Result
import com.example.pixbayphoto.domain.repository.PixabayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class DetailViewModel(
    private val pixabayRepository: PixabayRepository,
    private val id: Int,
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    init {
        fetchPixabay()
    }

    private fun fetchPixabay() {
        pixabayRepository.loadPhoto(query = "")
            .onStart {
                _state.update { it.copy(isLoading = true) }
            }
            .onCompletion {
                _state.update { it.copy(isLoading = false) }
            }
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        val pixabay = result.data.firstOrNull { it.id == id }
                        _state.update { it.copy(pixabay = pixabay) }
                    }

                    is Result.Failure -> _state.update { it.copy(error = "Error: ${result.message}") }
                }
            }
            .launchIn(viewModelScope)
    }
}