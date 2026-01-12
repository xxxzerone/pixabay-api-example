package com.example.pixbayphoto.data.repository

import android.content.Context
import com.example.pixbayphoto.R
import com.example.pixbayphoto.core.NetworkError
import com.example.pixbayphoto.core.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * PixabayRepositoryImpl의 동작을 테스트합니다.
 * Ktor Client의 MockEngine을 사용하여 네트워크 응답을 시뮬레이션합니다.
 */
class PixabayRepositoryImplTest {

    private lateinit var context: Context
    private lateinit var repository: PixabayRepositoryImpl

    @Before
    fun setUp() {
        // Context를 Mocking합니다. Repository 내부에서 API Key를 가져오기 위해 사용됩니다.
        context = mockk()
        every { context.getString(R.string.pixabay_key) } returns "dummy_key"
    }

    /**
     * 성공적인 JSON 응답을 시뮬레이션하여 데이터가 올바르게 파싱되고 Success 결과가 반환되는지 테스트합니다.
     */
    @Test
    fun `loadPhoto returns Success with mapped data when API call is successful`() = runTest {
        // Given: 정상적인 JSON 응답을 설정합니다.
        val mockEngine = MockEngine { request ->
            respond(
                content = """
                    {
                        "hits": [
                            {
                                "id": 1,
                                "user": "user1",
                                "tags": "tag1",
                                "previewURL": "url1"
                            }
                        ]
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        // Ktor Client에 MockEngine과 JSON 직렬화 설정을 주입합니다.
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }

        repository = PixabayRepositoryImpl(context, httpClient)

        // When: loadPhoto 함수를 호출하고 Flow 결과를 리스트로 수집합니다.
        val results = repository.loadPhoto("test").toList()

        // Then: 결과가 하나만 방출되었고, 그 결과가 성공(Success)인지 확인합니다.
        assertEquals(1, results.size)
        val result = results.first()
        assertTrue(result is Result.Success)

        // 데이터가 정확하게 매핑되었는지 검증합니다.
        val data = (result as Result.Success).data
        assertEquals(1, data.size)
        assertEquals(1, data[0].id)
        assertEquals("user1", data[0].user)
    }

    /**
     * HTTP 500 에러 응답을 시뮬레이션하여 HttpError가 반환되는지 테스트합니다.
     */
    @Test
    fun `loadPhoto returns HttpError when API returns 500 error`() = runTest {
        // Given: 500 Internal Server Error 응답을 설정합니다.
        val mockEngine = MockEngine {
            respondError(HttpStatusCode.InternalServerError)
        }

        val httpClient = HttpClient(mockEngine) {
             expectSuccess = true
        }
        repository = PixabayRepositoryImpl(context, httpClient)

        // When: loadPhoto를 호출합니다.
        val results = repository.loadPhoto("test").toList()

        // Then: 결과가 실패(Failure)이고, 에러 타입이 HttpError인지 확인합니다.
        val result = results.first()
        println("Result: $result")
        assertTrue("Expected Result.Failure but got $result", result is Result.Failure)
        val error = (result as Result.Failure).message
        assertTrue("Expected HttpError but got $error", error is NetworkError.HttpError)
        assertEquals(500, (error as NetworkError.HttpError).code)
    }

    /**
     * 잘못된 JSON 형식의 응답을 시뮬레이션하여 ParseError가 반환되는지 테스트합니다.
     */
    @Test
    fun `loadPhoto returns ParseError when JSON is malformed`() = runTest {
        // Given: SerializationException을 발생시키는 MockEngine을 설정합니다.
        val mockEngine = MockEngine {
            throw SerializationException("Forced error")
        }

        val httpClient = HttpClient(mockEngine) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        repository = PixabayRepositoryImpl(context, httpClient)

        // When: loadPhoto를 호출합니다.
        val results = repository.loadPhoto("test").toList()

        // Then: 결과가 실패(Failure)이고, 에러 타입이 ParseError인지 확인합니다.
        val result = results.first()
        println("Result: $result")
        if (result is Result.Success) {
            throw AssertionError("Expected Result.Failure but got Result.Success with data: ${result.data}")
        }
        assertTrue("Expected Result.Failure but got $result", result is Result.Failure)
        assertTrue("Expected ParseError but got ${(result as Result.Failure).message}", result.message is NetworkError.ParseError)
    }
}
