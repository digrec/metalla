package com.digrec.metalla.features.dashboard

import app.cash.turbine.test
import com.digrec.metalla.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/** Created by Dejan Igrec */
@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    private val viewModel = DashboardViewModel(mainDispatcherRule.dispatcherProvider)

    @Test
    fun `text should initially be Dashboard Screen`() = runTest {
        viewModel.text.test {
            assertEquals("Dashboard Screen", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
