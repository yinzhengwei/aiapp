package com.example.aiapp.base

import androidx.lifecycle.LifecycleOwner

/**
 * @author yzw
 * @date 2022/5/26
 * @describe
 */
interface IBaseView : LifecycleOwner {
    fun showLoading() {}
    fun hiddenLoading() {}
    fun showLoginWindow() {}
}