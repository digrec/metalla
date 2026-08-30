package com.digrec.metalla.features.home

import app.cash.turbine.test
import com.digrec.metalla.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/** Created by Dejan Igrec */
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    private val viewModel = HomeViewModel(mainDispatcherRule.dispatcherProvider)

    @Test
    fun `text should initially be Home Screen`() = runTest {
        viewModel.text.test {
            assertEquals("Home Screen", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
