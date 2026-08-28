package com.digrec.metalla.features.settings

import androidx.lifecycle.ViewModel
import com.digrec.metalla.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Created by Dejan Igrec */
class SettingsViewModel : ViewModel() {

    private val _appVersion =
        MutableStateFlow("v${BuildConfig.VERSION_NAME}+${BuildConfig.VERSION_CODE}")

    val appVersion: StateFlow<String> = _appVersion.asStateFlow()
}
