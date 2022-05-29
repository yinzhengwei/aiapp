package com.example.aiapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author yzw
 * @date 2022/5/26
 * @describe
 */
open class BaseViewModel : ViewModel() {

    lateinit var baseView: IBaseView
    fun <T> createLiveData() = MutableLiveData<T>()

}