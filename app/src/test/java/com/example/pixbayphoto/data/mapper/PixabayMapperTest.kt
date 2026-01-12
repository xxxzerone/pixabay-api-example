package com.example.pixbayphoto.data.mapper

import com.example.pixbayphoto.data.dto.PixabayDto
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * PixabayDto 데이터를 도메인 모델인 Pixabay로 변환하는 Mapper 로직을 테스트합니다.
 */
class PixabayMapperTest {

    @Test
    fun `toModel maps dto to domain model correctly`() {
        // Given: 테스트에 사용할 DTO 객체를 생성합니다. 모든 필드에 값이 있는 경우입니다.
        val dto = PixabayDto(
            id = 123,
            user = "test_user",
            tags = "tag1, tag2",
            previewURL = "http://example.com/image.jpg"
        )

        // When: DTO를 모델로 변환하는 확장 함수를 호출합니다.
        val model = dto.toModel()

        // Then: 변환된 모델의 각 필드가 예상한 값과 일치하는지 검증합니다.
        assertEquals(123, model.id) // ID 확인
        assertEquals("test_user", model.user) // 사용자 이름 확인
        assertEquals("tag1, tag2", model.tags) // 태그 확인
        assertEquals("http://example.com/image.jpg", model.previewURL) // URL 확인
    }

    @Test
    fun `toModel handles null values with default values`() {
        // Given: 모든 필드가 null인 DTO 객체를 생성합니다. API 응답에서 값이 누락될 수 있는 상황을 시뮬레이션합니다.
        val dto = PixabayDto(
            id = null,
            user = null,
            tags = null,
            previewURL = null
        )

        // When: DTO를 모델로 변환합니다.
        val model = dto.toModel()

        // Then: null 값들이 각각의 기본값(0 또는 빈 문자열)으로 적절히 변환되었는지 검증합니다.
        assertEquals(0, model.id) // null ID는 0으로 변환되어야 함
        assertEquals("", model.user) // null user는 빈 문자열로 변환되어야 함
        assertEquals("", model.tags) // null tags는 빈 문자열로 변환되어야 함
        assertEquals("", model.previewURL) // null URL은 빈 문자열로 변환되어야 함
    }
}
