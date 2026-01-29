package com.example.pixbayphoto.data.repository

import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
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
        val results = repository.getItemsSortedById("").first()

        // then
        Assert.assertTrue(results is Resource.Loading)
    }

    @Test
    fun `getItemsSortedById 호출 시 Loading 후 Success 데이터가 ID 내림차순으로 반환되어야 한다`() = runTest {
        // given
        // when
        // take(2): Loading과 Success 데이터까지만 받고 종료
        // toList(): Flow의 모든 이벤트를 리스트로 수집 (Loading, Success)
        val results = repository.getItemsSortedById("").take(2).toList()

        // then
        Assert.assertTrue(results[0] is Resource.Loading)

        val successResult = results[1] as Resource.Success
        val items = successResult.data

        val ids = items.map { it.id }
        val sortedIds = ids.sortedByDescending { it }

        Assert.assertEquals(10, items.size)
        Assert.assertEquals(sortedIds, ids)
        Assert.assertEquals(8252992L, items.first().id)
    }

    @Test
    fun `특정 검색어가 포함된 아이템만 필터링되어 반환되어야 한다`() = runTest {
        // given
        val query = "yellow rose"

        // when
        val result = repository.getItemsSortedById(query)
            .filter { it is Resource.Success }
            .first()

        // then
        Assert.assertTrue(result is Resource.Success)

        val items = (result as Resource.Success).data
        Assert.assertEquals(3, items.size)
        Assert.assertTrue(items.all { it.tags.contains(query) })
    }

    @Test
    fun `검색 결과가 없을 경우 빈 리스트를 반환해야 한다`() = runTest {
        // Arrange
        val query = "non-existent-tag"

        // Act
        val result = repository.getItemsSortedById(query)
            .filter { it is Resource.Success }
            .first()

        // Act
        val items = (result as Resource.Success).data
        Assert.assertTrue(items.isEmpty())
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
        Assert.assertEquals(targetId, result.data.id)
    }

    @Test
    fun `getItemById 호출 시 존재하지 않는 ID를 입력하면 Error를 반환한다`() = runTest {
        // given
        val nonExistentId = Long.MAX_VALUE

        // when
        val results = repository.getItemById(nonExistentId).take(2).toList()
        val errorResult = results[1] as Resource.Error

        // then
        Assert.assertTrue(results[0] is Resource.Loading)
        Assert.assertTrue(results[1] is Resource.Error)
        Assert.assertEquals("ID가 ${nonExistentId}인 아이템을 찾을 수 없습니다.", errorResult.message)
    }

}