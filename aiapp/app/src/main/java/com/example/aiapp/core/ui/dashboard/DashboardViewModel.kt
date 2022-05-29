package com.example.aiapp.core.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aiapp.base.BaseViewModel
import com.example.aiapp.net.ApiClient
import com.example.aiapp.net.RequestClient
import com.example.aiapp.util.ToastUtil

class DashboardViewModel : BaseViewModel() {

    private val _text = createLiveData<String>().apply {
        value = "onClick here to Login now!"
    }
    val text: LiveData<String> = _text

    fun loadData() {
        RequestClient.fetch(baseView, { ApiClient.testApi.getLgCollect() }, {
            ToastUtil.showToast(it.errorMsg)
        }, {
            ToastUtil.showToast(it)
        })
    }
}