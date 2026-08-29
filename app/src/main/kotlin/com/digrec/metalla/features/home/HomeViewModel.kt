package com.digrec.metalla.features.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Created by Dejan Igrec */
class HomeViewModel : ViewModel() {

    private val _text = MutableStateFlow("Home Screen")
    val text: StateFlow<String> = _text.asStateFlow()
}
