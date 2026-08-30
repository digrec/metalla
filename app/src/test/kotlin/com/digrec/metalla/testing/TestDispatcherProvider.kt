package com.digrec.metalla.testing

import com.digrec.metalla.core.domain.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/**
 * A [DispatcherProvider] for testing purposes.
 *
 * Created by Dejan Igrec
 */
class TestDispatcherProvider(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    DispatcherProvider {
    override val main: CoroutineDispatcher = testDispatcher
    override val mainImmediate: CoroutineDispatcher = testDispatcher
    override val io: CoroutineDispatcher = testDispatcher
    override val default: CoroutineDispatcher = testDispatcher
    override val unconfined: CoroutineDispatcher = testDispatcher
}
