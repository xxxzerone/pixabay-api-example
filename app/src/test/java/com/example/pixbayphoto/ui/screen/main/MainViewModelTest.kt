package com.example.pixbayphoto.ui.screen.main

import com.example.pixbayphoto.core.Result
import com.example.pixbayphoto.domain.model.Pixabay
import com.example.pixbayphoto.domain.repository.PixabayRepository
import com.example.pixbayphoto.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * MainViewModel의 비즈니스 로직을 테스트합니다.
 * 초기 데이터 로드, 검색어 변경에 따른 검색 동작 등을 검증합니다.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val pixabayRepository: PixabayRepository = mockk()
    private lateinit var viewModel: MainViewModel

    /**
     * ViewModel 초기화 시 자동으로 데이터를 로드하는지 테스트합니다.
     */
    @Test
    fun `init loads photos correctly`() = runTest {
        // Given: Repository가 성공적으로 데이터를 반환하도록 설정합니다.
        val expectedPhotos = listOf(
            Pixabay(1, "user1", "tags", "url"),
            Pixabay(2, "user2", "tags", "url")
        )
        // any()를 사용하여 인자 매칭을 유연하게 합니다.
        coEvery { pixabayRepository.loadPhoto(any()) } returns flowOf(Result.Success(expectedPhotos))

        // When: ViewModel을 초기화합니다.
        viewModel = MainViewModel(pixabayRepository)
        
        // 코루틴 실행 대기
        advanceUntilIdle()

        // Then: 상태가 업데이트되어 사진 목록이 포함되어 있는지 확인합니다.
        val state = viewModel.state.value
        assertEquals(expectedPhotos, state.pixabays)
        assertFalse(state.isLoading)
        assertEquals(null, state.error)
    }

    /**
     * 검색어가 변경되면 디바운스 시간 이후에 새로운 검색을 수행하는지 테스트합니다.
     */
    @Test
    fun `search query change triggers fetch after debounce`() = runTest {
        // Given: 초기 로드와 검색 로드에 대한 Mock 응답을 설정합니다.
        coEvery { pixabayRepository.loadPhoto(any()) } returns flowOf(Result.Success(emptyList()))
        
        val searchResult = listOf(Pixabay(3, "searchUser", "tag", "url"))
        coEvery { pixabayRepository.loadPhoto("apple") } returns flowOf(Result.Success(searchResult))

        viewModel = MainViewModel(pixabayRepository)
        advanceUntilIdle() // 초기 로드 완료 처리
        
        // When: 검색어를 변경합니다.
        viewModel.handleActon(MainAction.OnValueChange("apple"))

        // 디바운스 시간(500ms) 이전에는 검색이 실행되지 않아야 합니다.
        advanceTimeBy(300)
        runCurrent() // 대기 중인 작업 실행
        assertEquals(emptyList<Pixabay>(), viewModel.state.value.pixabays)

        // 디바운스 시간을 충분히 기다립니다.
        advanceTimeBy(300) // 총 600ms 경과
        runCurrent() // 대기 중인 작업 실행

        // Then: 검색 결과로 상태가 업데이트되었는지 확인합니다.
        assertEquals(searchResult, viewModel.state.value.pixabays)
        assertEquals("apple", viewModel.state.value.query)
    }

    @Test
    fun `loading state updates correctly`() = runTest {
        // Given
        coEvery { pixabayRepository.loadPhoto(any()) } returns flowOf(Result.Success(emptyList()))
        viewModel = MainViewModel(pixabayRepository)
        advanceUntilIdle()
        
        // When
        viewModel.handleActon(MainAction.OnSearchAction("test"))
        advanceUntilIdle()
        
        // Then
        assertFalse(viewModel.state.value.isLoading)
    }
}
