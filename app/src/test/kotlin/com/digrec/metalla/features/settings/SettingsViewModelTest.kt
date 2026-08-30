package com.digrec.metalla.features.settings

import app.cash.turbine.test
import com.digrec.metalla.BuildConfig
import com.digrec.metalla.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/** Created by Dejan Igrec */
@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    private val viewModel = SettingsViewModel(mainDispatcherRule.dispatcherProvider)

    @Test
    fun `appVersion should initially match BuildConfig`() = runTest {
        viewModel.appVersion.test {
            assertEquals("v${BuildConfig.VERSION_NAME}+${BuildConfig.VERSION_CODE}", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
