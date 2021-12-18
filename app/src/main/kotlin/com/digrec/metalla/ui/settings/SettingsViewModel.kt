package com.digrec.metalla.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digrec.metalla.BuildConfig


/**
 * Created by Dejan Igrec
 */
class SettingsViewModel : ViewModel() {

    private val _appVersion = MutableLiveData<String>().apply {
        value = "v${BuildConfig.VERSION_NAME}+${BuildConfig.VERSION_CODE}"
    }

    val appVersion: LiveData<String> = _appVersion
}
