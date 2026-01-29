package com.example.pixbayphoto.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test

class DefaultDataSourceTest {

    private val testApiKey = "test_api_key"
    private lateinit var mockEngine: MockEngine
    private lateinit var dataSource: DataSource

    private val jsonResponse = """
        {
            "total": 1,
            "totalHits": 1,
            "hits": [
                {
                    "id": 123,
                    "pageURL": "https://example.com",
                    "type": "photo",
                    "tags": "flower",
                    "previewURL": "https://example.com/preview.jpg",
                    "user": "tester"
                }
            ]
        }
    """.trimIndent()

    @Test
    fun `fetchItems 호출 시 올바른 URL과 파라미터가 전달되어야 한다`() = runTest {
        // Arrange
        createDataSource { request ->
            Assert.assertEquals("https://pixabay.com/api/", request.url.toString().substringBefore("?"))
            Assert.assertEquals(testApiKey, request.url.parameters["key"])
            Assert.assertEquals("rose", request.url.parameters["q"])
            Assert.assertEquals("photo", request.url.parameters["image_type"])

            respond(
                content = jsonResponse,
                status = HttpStatusCode.Companion.OK,
                headers = headersOf(HttpHeaders.ContentType, "${ContentType.Application.Json}")
            )
        }

        // Act
        dataSource.fetchItems("rose")
    }

    @Test
    fun `서버 응답이 성공적일 때 Response 객체에 데이터가 올바르게 담겨야 한다`() = runTest {
        // Arrange
        createDataSource {
            respond(
                content = jsonResponse,
                status = HttpStatusCode.Companion.OK,
                headers = headersOf(HttpHeaders.ContentType, "${ContentType.Application.Json}")
            )
        }

        // Act
        val response = dataSource.fetchItems("flower")

        // Assert
        Assert.assertEquals(HttpStatusCode.Companion.OK.value, response.statusCode)
        Assert.assertNotNull(response.body)
        Assert.assertEquals(123L, response.body?.hits?.first()?.id)
    }

    @Test
    fun `서버 에러 발생 시 body는 null이고 에러 코드가 반환되어야 한다`() = runTest {
        // Arrange
        createDataSource {
            respond(
                content = "Internal Server Error",
                status = HttpStatusCode.Companion.InternalServerError
            )
        }

        // Act
        val response = dataSource.fetchItems("error")

        // Assert
        Assert.assertEquals(500, response.statusCode)
        Assert.assertNull(response.body)
    }

    @Test
    fun `네트워크 예외 발생 시 statusCode가 -1인 Response를 반환해야 한다`() = runTest {
        // Arrange
        createDataSource {
            throw Exception("Network Timeout")
        }

        // Act
        val response = dataSource.fetchItems("timeout")

        // Assert
        Assert.assertEquals(-1, response.statusCode)
        Assert.assertNull(response.body)
    }

    private fun createDataSource(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData) {
        mockEngine = MockEngine.Companion { request ->
            handler(request)
        }
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
        dataSource = DefaultDataSource(httpClient, testApiKey)
    }
}