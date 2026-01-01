package com.example.pixbayphoto.ui.screen.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixbayphoto.R
import com.example.pixbayphoto.data.api.pixabayService
import com.example.pixbayphoto.data.mapper.toModel
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
    context: Context,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    private val _pixabayKey = context.getString(R.string.pixabay_key)

    private val _query = _state.map { it.query }
        .distinctUntilChanged()
        .debounce(1000)

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
            try {
                val response = pixabayService.loadPixabayData(
                    key = _pixabayKey,
                    q = query,
                    type = "photo"
                )
                Log.d("MainViewModel fetchPixabay", "${response.hits}")

                _state.update {
                    it.copy(
                        pixabays = response.hits?.map { pixabayDto ->
                            pixabayDto.toModel()
                        } ?: emptyList(),
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