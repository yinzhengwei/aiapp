package com.example.aiapp.core.ui.home

import com.example.aiapp.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    val text = createLiveData<String>().apply {
        value = "This is home Fragment"
    }

}