package com.example.pixbayphoto.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.usecase.GetItemById
import com.example.pixbayphoto.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getItemById: GetItemById
) : ViewModel() {

    private val _detailRoute: Route.Detail = savedStateHandle.toRoute()
    private val _itemId = _detailRoute.itemId

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<DetailEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchItemDetail()
    }

    fun onAction(action: DetailAction) {
        // TODO: Implement action
    }

    private fun fetchItemDetail() {
        getItemById(_itemId)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success<Item> -> _state.update {
                        it.copy(
                            item = resource.data,
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
}