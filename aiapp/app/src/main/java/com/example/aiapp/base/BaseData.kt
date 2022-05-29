package com.example.aiapp.base

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * @author yzw
 * @date 2022/5/26
 * @describe
 */
@Parcelize
open class BaseData : Parcelable {
    @IgnoredOnParcel
    var errorCode = -1

    @IgnoredOnParcel
    var errorMsg = ""

    //判断是否成功
    fun isSuccessful() = errorCode == 0

    //判断是否登陆
    fun isLogin() = errorCode != -1001
}