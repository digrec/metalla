package com.digrec.metalla.features.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Created by Dejan Igrec */
class DashboardViewModel : ViewModel() {

    private val _text = MutableStateFlow("Dashboard Fragment")
    val text: StateFlow<String> = _text.asStateFlow()
}
