package com.example.pixbayphoto.domain.usecase

import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import com.example.pixbayphoto.util.MockItems
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetItemsSortedByIdUseCaseTest {

    private val repository: ItemRepository = mockk()
    private lateinit var useCase: GetItemsSortedByIdUseCase

    @Before
    fun setUp() {
        useCase = GetItemsSortedByIdUseCase(repository)
    }

    @Test
    fun `UseCase 호출 시 Loading 후 Success 데이터가 ID 내림차순으로 반환된다`() = runTest {
        // given
        val mockItems = MockItems.items
        val expectedFlow = flowOf(
            Resource.Loading,
            Resource.Success(mockItems)
        )
        every { repository.getItemsSortedById() } returns expectedFlow

        // when
        val result = useCase().toList()

        // then
        verify { repository.getItemsSortedById() }

        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(mockItems, (result[1] as Resource.Success).data)
    }

    @Test
    fun `에러 발생 시 에러 상태를 반환해야 한다`() = runTest {
        // given
        val errorMessage = "네트워크 오류"
        val expectedFlow = flowOf(
            Resource.Loading,
            Resource.Error(message = errorMessage)
        )
        every { repository.getItemsSortedById() } returns expectedFlow

        // when
        val result = useCase().toList()

        // then
        verify { repository.getItemsSortedById() }

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals(errorMessage, (result[1] as Resource.Error).message)
    }
}