package com.eloir.todoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextViewModel: ViewModel() {
    var name = MutableLiveData<String>()
    var desc = MutableLiveData<String>()
}