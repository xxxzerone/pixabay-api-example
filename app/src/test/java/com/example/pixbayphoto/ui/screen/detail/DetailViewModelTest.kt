package com.example.pixbayphoto.ui.screen.detail

import com.example.pixbayphoto.core.Result
import com.example.pixbayphoto.domain.model.Pixabay
import com.example.pixbayphoto.domain.repository.PixabayRepository
import com.example.pixbayphoto.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

/**
 * DetailViewModel의 동작을 테스트합니다.
 * ID에 해당하는 특정 사진 정보를 로드하는 로직을 검증합니다.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val pixabayRepository: PixabayRepository = mockk()
    private lateinit var viewModel: DetailViewModel

    @Test
    fun `init finds correct photo by id`() = runTest {
        // Given: 테스트용 사진 데이터 목록을 설정합니다.
        val targetPhoto = Pixabay(100, "user1", "tag1", "url1")
        val otherPhoto = Pixabay(200, "user2", "tag2", "url2")
        val photos = listOf(targetPhoto, otherPhoto)

        // Repository가 해당 목록을 반환하도록 설정합니다.
        coEvery { pixabayRepository.loadPhoto("") } returns flowOf(Result.Success(photos))

        // When: ID가 100인 사진을 상세 조회하기 위해 ViewModel을 초기화합니다.
        viewModel = DetailViewModel(pixabayRepository, id = 100)

        // Then: 상태(State)에 올바른 사진 정보가 설정되었는지 확인합니다.
        val state = viewModel.state.value
        assertEquals(targetPhoto, state.pixabay)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `init handles non-existent id gracefully`() = runTest {
        // Given: 찾고자 하는 ID가 목록에 없는 경우를 설정합니다.
        val photos = listOf(
            Pixabay(200, "user2", "tag2", "url2")
        )
        coEvery { pixabayRepository.loadPhoto("") } returns flowOf(Result.Success(photos))

        // When: 존재하지 않는 ID(999)로 ViewModel을 초기화합니다.
        viewModel = DetailViewModel(pixabayRepository, id = 999)

        // Then: 선택된 사진(pixabay)이 null이어야 합니다.
        val state = viewModel.state.value
        assertNull(state.pixabay)
    }
}
