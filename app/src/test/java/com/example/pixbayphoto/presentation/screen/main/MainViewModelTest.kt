package com.example.pixbayphoto.presentation.screen.main

import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.usecase.GetItemsSortedByIdUseCase
import com.example.pixbayphoto.util.MainDispatcherRule
import com.example.pixbayphoto.util.MockItems
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase: GetItemsSortedByIdUseCase = mockk()
    private lateinit var viewModel: MainViewModel

    @Test
    fun `초기화 시 fetchItems 메서드가 호출되고 Loading 상태를 반영한다`() = runTest {
        // given
        every { useCase() } returns flowOf(Resource.Loading)

        // when
        viewModel = MainViewModel(useCase)

        // then
        verify { useCase() }

        assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `아이템 로드 성공 시 isLoading은 false가 되어야 한다`() = runTest {
        // given
        every { useCase() } returns flowOf(Resource.Loading, Resource.Success(emptyList()))

        // when
        viewModel = MainViewModel(useCase)

        // then
        verify { useCase() }

        assertFalse(viewModel.state.value.isLoading)
        assertEquals(emptyList<Item>(), viewModel.state.value.items)
    }

    @Test
    fun `아이템 로드 성공 시 state에 아이템이 갱신된다`() = runTest {
        // given
        val mockItems = MockItems.items
        every { useCase() } returns flowOf(Resource.Loading, Resource.Success(mockItems))

        // when
        viewModel = MainViewModel(useCase)
        /**
         * fetchItems에서 viewModelScope는 비동기로 동작한다.
         * advanceUntilIdle()을 호출해야 Flow의 모든 연산이 완료된 후의 최종 값을 확인할 수 있다.
         */
        advanceUntilIdle()

        // then
        verify { useCase() }

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(mockItems, state.items)
        assertEquals(null, state.error)
    }
}