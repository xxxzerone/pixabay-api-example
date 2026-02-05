package com.example.pixbayphoto.data.repository

import com.example.pixbayphoto.domain.repository.ItemRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MockItemRepositoryTest {

    private lateinit var repository: ItemRepository

    @Before
    fun setUp() {
        repository = MockItemRepository()
    }

    @Test
    fun `getItemsSortedById() 검색어가 비어있으면 모든 아이템을 반환한다`() = runTest {
        // arrange
        val query = ""
        val expected = MockItemRepository.MOCK_ITEMS.first()
        val expectedSortedItems = expected.sortedByDescending { it.id }

        // act
        val items = repository.getItemsSortedById(query).first()

        // assert
        assertEquals(expected.size, items.size)
        assertEquals(expectedSortedItems[0].id, items[0].id)
        assertEquals(expectedSortedItems[expected.size - 1].id, items[items.size - 1].id)
    }

    @Test
    fun `getItemsSortedBy() 특정 키워드로 검색하면 해당 태그가 포함된 아이템만 반환한다`() = runTest {
        // arrange
        val query = "rose"
        val expectedId = 6162613L

        // act
        val items = repository.getItemsSortedById(query).first()

        // assert
        assertEquals(1, items.size)
        assertEquals(expectedId, items.first().id)
        assertTrue(items.any { it.tags.contains(query, ignoreCase = true) })
    }

    @Test
    fun `getItemById() 존재하는 ID로 조회하면 해당 아이템을 반환한다`() = runTest {
        // arrange
        val targetId = 6162613L
        val userId = 9363663L

        // act
        val item = repository.getItemById(targetId).first()

        // assert
        assertEquals(targetId, item?.id)
        assertEquals(userId, item?.userId)
    }

    @Test
    fun `getItemById() 존재하지 않는 ID로 조회하면 null이 반환된다`() = runTest {
        // arrange
        val targetId = Long.MAX_VALUE

        // act
        val item = repository.getItemById(targetId).first()

        // assert
        assertNull(item)
    }
}
