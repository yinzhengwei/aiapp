@file:Suppress("UNCHECKED_CAST")

package com.example.aiapp.net

import com.example.aiapp.R
import com.example.aiapp.base.BaseData
import com.example.aiapp.base.IBaseView
import com.example.aiapp.config.ParamsConstant
import com.example.aiapp.util.LaunchUtil
import kotlinx.coroutines.runBlocking

/**
 * @author yzw
 * @date 2022/5/26
 * @describe 统一的网络请求入口
 */
object RequestClient {

    fun <T> fetch(
        baseView: IBaseView,
        request: suspend () -> T,//将retrofit中的方法传进来
        respCallback: (T) -> Unit,
        failCallback: (String) -> Unit = {}//可选的回调
    ) {
        baseView.showLoading()
        LaunchUtil.execute({//开启协程，并发出请求
            runBlocking {
                return@runBlocking try {
                    request()//执行retrofit请求
                } catch (e: Exception) {
                    e.printStackTrace()
                    e
                }
            }
        }, {
            baseView.hiddenLoading()
            it ?: return@execute
            when (it) {
                //请求失败
                is Exception -> failCallback(ParamsConstant.mContext.getString(R.string.error_tip))
                is BaseData -> {
                    if (!it.isLogin()) {//未登录
                        baseView.showLoginWindow()
                        return@execute
                    }
                    if (it.isSuccessful()) {//是否成功
                        respCallback(it as T)
                        return@execute
                    }
                    //返回的状态值是不OK的
                    failCallback(it.errorMsg)
                }
            }
        })
    }

}