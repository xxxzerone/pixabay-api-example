package com.example.pixbayphoto.data.repository

import com.example.pixbayphoto.data.datasource.DataSource
import com.example.pixbayphoto.data.mapper.ItemMapper
import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.common.Response
import com.example.pixbayphoto.domain.common.isSuccess
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val dataSource: DataSource,
    private val mapper: ItemMapper,
) : ItemRepository {

    override fun getItemsSortedById(query: String): Flow<Resource<List<Item>>> = safeApiCall(
        apiCall = { dataSource.fetchQueryItems(query) },
        transform = { body ->
            Resource.Success(mapper.toDomainList(body))
        }
    )

    override fun getItemById(id: Long): Flow<Resource<Item>> = safeApiCall(
        apiCall = { dataSource.fetchItemById(id) },
        transform = { body ->
            val itemDto = body.hits?.firstOrNull { it.id == id }
            if (itemDto != null) {
                Resource.Success(mapper.toDomain(itemDto))
            } else {
                Resource.Error("해당 ID($id)의 아이템을 찾을 수 없습니다.")
            }
        }
    )

    private fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<T>,
        transform: (T) -> Resource<R>,
    ): Flow<Resource<R>> = flow {
        emit(Resource.Loading)
        val response = apiCall()

        if (response.isSuccess() && response.body != null) {
            emit(transform(response.body))
        } else {
            emit(Resource.Error("Error code: ${response.statusCode}"))
        }
    }.catch { e ->
        emit(Resource.Error(e.localizedMessage ?: "알 수 없는 오류 발생", e))
    }
}