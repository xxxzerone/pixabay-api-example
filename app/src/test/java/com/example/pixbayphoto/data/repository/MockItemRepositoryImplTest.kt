package com.example.pixbayphoto.data.repository

import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MockItemRepositoryImplTest {

    private lateinit var repository: ItemRepository

    @Before
    fun setUp() {
        repository = MockItemRepositoryImpl()
    }

    @Test
    fun `getItemsSortedById 호출 시 처음에는 Loading이 반환된다`() = runTest {
        // given
        // when
        val results = repository.getItemsSortedById().first()

        // then
        assertTrue(results is Resource.Loading)
    }

    @Test
    fun `getItemsSortedById 호출 시 Loading 후 Success 데이터가 ID 내림차순으로 반환되어야 한다`() = runTest {
        // given
        // when
        // take(2): Loading과 Success 데이터까지만 받고 종료
        // toList(): Flow의 모든 이벤트를 리스트로 수집 (Loading, Success)
        val results = repository.getItemsSortedById().take(2).toList()

        // then
        assertTrue(results[0] is Resource.Loading)

        val successResult = results[1] as Resource.Success
        val items = successResult.data

        val ids = items.map { it.id }
        val sortedIds = ids.sortedByDescending { it }

        assertEquals(10, items.size)
        assertEquals(sortedIds, ids)
        assertEquals(8252992L, items.first().id)
    }

    @Test
    fun `getItemById 호출 시 존재하는 ID를 입력하면 해당 아이템을 반환한다`() = runTest {
        // given
        val targetId = 8114428L

        // when
        // Loading 상태를 무시하고 첫 번째 Success 데이터만 가져오고 싶을 때
        val result = repository.getItemById(targetId)
            .filterIsInstance<Resource.Success<Item>>()
            .first()

        // then
        assertEquals(targetId, result.data.id)
    }

    @Test
    fun `getItemById 호출 시 존재하지 않는 ID를 입력하면 null을 반환한다`() = runTest {
        // given
        val nonExistentId = Long.MAX_VALUE

        // when
        val results = repository.getItemById(nonExistentId).take(2).toList()
        val errorResult = results[1] as Resource.Error

        // then
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Error)
        assertEquals("ID가 ${nonExistentId}인 아이템을 찾을 수 없습니다.", errorResult.message)
    }
}