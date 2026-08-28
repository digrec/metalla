package com.digrec.metalla.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/** Created by Dejan Igrec */
class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply { value = "Home Fragment" }
    val text: LiveData<String> = _text
}
