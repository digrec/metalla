package com.digrec.metalla.features.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Created by Dejan Igrec
 */
class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Dashboard Fragment"
    }
    val text: LiveData<String> = _text
}
