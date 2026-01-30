package com.example.pixbayphoto.di

import com.example.pixbayphoto.data.repository.MockItemRepositoryImpl
import com.example.pixbayphoto.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Binds와 @Provides는 역할이 다르다
 * @Binds → “타입 관계를 선언”
 * @Provides → “객체 생성 방법을 제공”
 *
 * 그래서 인터페이스 ↔ 구현체는 ‘관계’ 문제라 @Binds,
 * 외부 라이브러리는 ‘생성’ 문제라 @Provides를 쓰는 게 맞다.
 *
 * 핵심 이유 ① : 의미가 다르다 (의도 표현)
 * @Provides의 의미
 * “내가 직접 이 객체를 이렇게 만들어줄게”
 *
 * @Binds의 의미
 * “이 타입은 이 타입으로 치환해”
 *
 * 핵심 이유 ② : 책임 분리 (SRP)
 * @Provides로 인터페이스 연결하면?
 * @Provides
 * fun provideItemRepository(api: Api): ItemRepository {
 *     return ItemRepositoryImpl(api)
 * }
 * 이 함수는 동시에:
 * 1. 구현체 선택
 * 2. 객체 생성
 * 3. 의존성 조립
 * 👉 책임이 3개
 *
 * @Binds + @Inject constructor 의미
 *
 * class ItemRepositoryImpl @Inject constructor(api: Api)
 * @Binds
 * abstract fun bindItemRepository(
 *     impl: ItemRepositoryImpl
 * ): ItemRepository
 * 책임이 분리됨:
 * 1. 생성 책임 → ItemRepositoryImpl
 * 2. 타입 매핑 → @Binds
 * 👉 구조가 단순 + 명확
 *
 * 핵심 이유 ③ : 컴파일 타임 최적화 (중요 🔥)
 * Dagger/Hilt 내부 동작 차이야.
 *
 * @Binds
 * 1. 메서드 바디 없음
 * 2. 단순 타입 매핑
 * 3. 완전한 컴파일 타임 처리
 * 👉 더 빠르고, 그래프 단순
 *
 * @Provides
 * 1. 메서드 호출 필요
 * 2. 런타임에 팩토리 코드 생성
 * 3. 상대적으로 비용 있음
 * 👉 그래서 공식 문서도 말함:
 * “가능하면 @Binds를 써라”
 *
 * 핵심 이유 ④ : 리팩토링 & 테스트
 * 구현체 교체할 때
 * @Binds 👉 한 줄 교체
 * @Provides 👉 생성 로직까지 신경 써야 함
 *
 * 핵심 이유 ⑤ : “DI 철학” 차이
 * DI의 핵심은 이거야:
 * “객체를 만드는 방법보다
 * 객체 간의 관계를 먼저 선언하자”
 *
 * - 관계 → @Binds
 * - 생성 → @Provides
 * 그래서 둘이 분리돼 있음.
 *
 * 마지막으로 한 문장으로 정리 (진짜 끝 🔥)
 * 인터페이스 ↔ 구현체는 “타입 관계” 문제라 @Binds,
 * 외부 라이브러리 / 생성 로직은 “객체 생성” 문제라 @Provides
 *
 * 그리고 @Provides로 할 수 있어도,
 * 그건 ‘덜 좋은 선택’일 뿐 ‘틀린 선택’은 아니다
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideItemRepository(impl: MockItemRepositoryImpl): ItemRepository
}