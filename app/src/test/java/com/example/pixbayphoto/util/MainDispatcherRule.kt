package com.example.pixbayphoto.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * 단위 테스트에서 코루틴의 Main Dispatcher를 설정하고 해제하는 JUnit Rule입니다.
 * ViewModel 테스트와 같이 Main Thread에서 동작해야 하는 코드를 테스트할 때 사용됩니다.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    // 테스트 시작 시 호출되어 Main Dispatcher를 TestDispatcher로 설정합니다.
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    // 테스트 종료 시 호출되어 Main Dispatcher 설정을 초기화합니다.
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
