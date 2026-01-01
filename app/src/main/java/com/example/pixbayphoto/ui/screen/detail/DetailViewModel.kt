package com.example.pixbayphoto.ui.screen.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixbayphoto.R
import com.example.pixbayphoto.data.api.pixabayService
import com.example.pixbayphoto.data.mapper.toModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    context: Context,
    private val id: Int
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val _pixabayKey = context.getString(R.string.pixabay_key)

    init {
        fetchPixabay()
    }

    private fun fetchPixabay() {
        viewModelScope.launch {
            try {
                val response = pixabayService.loadPixabayData(
                    key = _pixabayKey,
                    q = "",
                    type = "photo"
                )

                Log.d("MainViewModel fetchPixabay", "${response.hits}")
                val pixabay = response.hits?.firstOrNull() { it.id == id }?.toModel()

                _state.update {
                    it.copy(
                        pixabay = pixabay,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                Log.e("MainViewModel fetchPixabay", "${e.message}")
                _state.update {
                    it.copy(isLoading = false, error = "Fetching Pixabay Error: ${e.message}")
                }
            }
        }
    }
}