package com.example.pixbayphoto.data.repository

import com.example.pixbayphoto.data.datasource.DataSource
import com.example.pixbayphoto.data.dto.ItemDto
import com.example.pixbayphoto.data.dto.ItemResponse
import com.example.pixbayphoto.data.mapper.ItemMapper
import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.common.Response
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ItemRepositoryImplTest {

    private val dataSource: DataSource = mockk()
    private val mapper: ItemMapper = mockk()
    private lateinit var repository: ItemRepository

    private val testQuery = "rose"
    private val targetId = 1L

    private val mockItemDto = ItemDto(
        id = 1L,
        pageUrl = "https://example.com",
        type = "photo",
        tags = "rose, flower",
        previewUrl = "https://example.com/preview.jpg",
        user = "tester"
    )
    private val mockResponse = ItemResponse(hits = listOf(mockItemDto))

    private val mockItem = Item(
        id = 1L,
        pageUrl = "https://example.com",
        type = "photo",
        tags = "rose, flower",
        previewUrl = "https://example.com/preview.jpg",
        user = "tester"
    )

    @Before
    fun setUp() {
        repository = ItemRepositoryImpl(dataSource, mapper)
    }

    @Test
    fun `getItemsSortedById 호출 시 성공하면 Loading, Success 상태를 순서대로 방출해야 한다`() = runTest {
        // Arrange
        val successResponse = Response(
            headers = emptyMap(),
            statusCode = 200,
            body = mockResponse
        )

        coEvery { dataSource.fetchQueryItems(testQuery) } returns successResponse
        every { mapper.toDomainList(mockResponse) } returns listOf(mockItem)

        // Act
        val result = repository.getItemsSortedById(testQuery).toList()

        // Assert
        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val data = (result[1] as Resource.Success).data
        assertEquals(1, data.size)
        assertEquals(mockItem, data[0])
    }

    @Test
    fun `API 응답이 실패하면 Loading, Error 순서로 방출해야 한다`() = runTest {
        // Arrange
        val errorResponse = Response<ItemResponse>(
            headers = emptyMap(),
            statusCode = 404,
            body = null
        )

        coEvery { dataSource.fetchQueryItems(testQuery) } returns errorResponse

        // Act
        val result = repository.getItemsSortedById(testQuery).toList()

        // Assert
        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Error code: 404", (result[1] as Resource.Error).message)
    }

    @Test
    fun `예외가 발생하면 catch 블록을 통해 Error를 방출해야 한다`() = runTest {
        // Arrange
        val exceptionMessage = "Network Timeout"
        coEvery { dataSource.fetchQueryItems(testQuery) } throws Exception(exceptionMessage)

        // Act
        val result = repository.getItemsSortedById(testQuery).toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals(exceptionMessage, (result[1] as Resource.Error).message)
    }

    @Test
    fun `getItemById 호출 시 해당 ID의 아이템이 존재하면 Success를 방출해야 한다`() = runTest {
        // Arrange
        val successResponse = Response(
            headers = emptyMap(),
            statusCode = 200,
            body = mockResponse
        )

        coEvery { dataSource.fetchItemById(targetId) } returns successResponse
        every { mapper.toDomain(mockItemDto) } returns mockItem

        // Act
        val result = repository.getItemById(targetId).toList()

        // Assert
        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val successResult = result[1] as Resource.Success
        assertEquals(targetId, successResult.data.id)
        assertEquals(mockItem, successResult.data)

        coVerify { dataSource.fetchItemById(targetId) }
        verify { mapper.toDomain(mockItemDto) }
    }

    @Test
    fun `존재하지 않는 ID로 호출하면 400 Error를 방출해야 한다`() = runTest {
        // Arrange
        val errorResponse = Response<ItemResponse>(
            headers = emptyMap(),
            statusCode = 400,
            body = null
        )

        coEvery { dataSource.fetchItemById(Long.MAX_VALUE) } returns errorResponse

        // Act
        val result = repository.getItemById(Long.MAX_VALUE).toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Error code: 400", (result[1] as Resource.Error).message)
    }

    @Test
    fun `getItemById 호출 시 서버 응답 자체가 실패한 경우 Error를 방출해야 한다`() = runTest {
        // arrange
        val errorResponse = Response<ItemResponse>(
            headers = emptyMap(),
            statusCode = 500,
            body = null
        )

        coEvery { dataSource.fetchItemById(targetId) } returns errorResponse

        // act
        val result = repository.getItemById(targetId).toList()

        // assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Error code: 500", (result[1] as Resource.Error).message)
    }
}