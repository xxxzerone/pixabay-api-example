package com.example.pixbayphoto.domain.usecase

import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.repository.ItemRepository
import com.example.pixbayphoto.util.MockItems
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetItemByIdTest {

    private val repository: ItemRepository = mockk()
    private lateinit var useCase: GetItemById

    @Before
    fun setUp() {
        useCase = GetItemById(repository)
    }

    @Test
    fun `ID를 입력하면 해당 아이템을 반환한다`() = runTest {
        // given
        val mockItem = MockItems.items[0]
        val targetId = mockItem.id

        val expectedFlow = flowOf(
            Resource.Loading,
            Resource.Success(mockItem)
        )
        every { repository.getItemById(targetId) } returns expectedFlow

        // when
        val result = useCase(targetId).toList()

        // then
        verify { repository.getItemById(targetId) }

        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(mockItem, (result[1] as Resource.Success).data)
    }

    @Test
    fun `존재하지 않는 ID를 입력하면 Error를 반환한다`() = runTest {
        // given
        val targetId = Long.MAX_VALUE
        val errorMessage = "ID가 ${targetId}인 아이템을 찾을 수 없습니다."
        val expectedFlow = flowOf(
            Resource.Loading,
            Resource.Error(message = errorMessage)
        )
        every { repository.getItemById(targetId) } returns expectedFlow

        // when
        val result = useCase(targetId).toList()

        verify { repository.getItemById(targetId) }

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals(errorMessage, (result[1] as Resource.Error).message)
    }
}