package com.example.pixbayphoto.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.usecase.GetItemByIdUseCase
import com.example.pixbayphoto.presentation.navigation.Route
import com.example.pixbayphoto.util.MainDispatcherRule
import com.example.pixbayphoto.util.MockItems
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getItemByIdUseCase: GetItemByIdUseCase = mockk()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: DetailViewModel

    private val mockItemId = MockItems.items[0].id
    private val mockItem = MockItems.items[0]

    @Before
    fun setUp() {
        // 1. 확장 함수 모킹을 위해 static 모킹 선언
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        savedStateHandle = mockk(relaxed = true) // 실제 생성 대신 모킹 객체 사용
        // 2. savedStateHandle.toRoute<Route.Detail>() 호출 시 가짜 객체 반환 설정
        every { savedStateHandle.toRoute<Route.Detail>() } returns Route.Detail(itemId = mockItemId)
    }

    @After
    fun tearDown() {
        unmockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    @Test
    fun `로딩 상태일 때 state의 isLoading만 true여야 한다`() = runTest {
        // Arrange
        every { getItemByIdUseCase(mockItemId) } returns flowOf(Resource.Loading)

        // Act
        viewModel = DetailViewModel(savedStateHandle, getItemByIdUseCase)

        // Assert
        verify(exactly = 1) { getItemByIdUseCase(mockItemId) }

        val state = viewModel.state.value
        assertTrue(state.isLoading)
        assertNull(state.item)
        assertNull(state.error)
    }

    @Test
    fun `초기화 시 SavedStateHandle에서 itemId를 읽고 데이터를 요청해야 한다`() = runTest {
        // Arrange
        every { getItemByIdUseCase(mockItemId) } returns flowOf(Resource.Loading)

        // Act
        viewModel = DetailViewModel(savedStateHandle, getItemByIdUseCase)

        // Assert
        verify(exactly = 1) { getItemByIdUseCase(mockItemId) }

        assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `데이터 로드 성공 시 state에 반영되고 로딩이 종료되어야 한다`() = runTest {
        // Arrange
        every { getItemByIdUseCase(mockItemId) } returns flowOf(
            Resource.Loading,
            Resource.Success(mockItem)
        )

        // Act
        viewModel = DetailViewModel(savedStateHandle, getItemByIdUseCase)

        // Assert
        verify(exactly = 1) { getItemByIdUseCase(mockItemId) }

        val state = viewModel.state.value
        assertEquals(mockItem, state.item)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `데이터 로드 실패 시 에러 메시지가 state에 반영되고 로딩이 종료되어야 한다`() = runTest {
        // Arrange
        val errorMessage = "아이템을 찾을 수 없습니다."
        every { getItemByIdUseCase(mockItemId) } returns flowOf(
            Resource.Loading,
            Resource.Error(errorMessage)
        )

        // Act
        viewModel = DetailViewModel(savedStateHandle, getItemByIdUseCase)

        // Assert
        verify(exactly = 1) { getItemByIdUseCase(mockItemId) }

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(errorMessage, state.error)
        assertNull(state.item)
    }

}