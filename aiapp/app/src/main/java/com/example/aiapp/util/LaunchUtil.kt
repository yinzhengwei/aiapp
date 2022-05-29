package com.example.aiapp.util

import kotlinx.coroutines.*

/**
 * @author yzw
 * @date 2022/5/26
 * @describe 协程管理类
 */
object LaunchUtil {

    /**
     * 启动协程
     *
     * @param1 callbackIO 子线程上下文
     * @param2 callbackMAIN 主线程上下文 (可选)
     */
    fun <T> execute(callbackIO: () -> T, callbackMAIN: (T) -> Unit = {}): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            val async = async {
                callbackIO()
            }
            val result = async.await()
            withContext(Dispatchers.Main) {
                callbackMAIN(result)
            }
        }
    }

    /**
     * 切换到主线程
     *
     * @param callbackMAIN 主线程上下文 (可选)
     */
    fun changeToMain(callbackMAIN: () -> Unit) {
        runBlocking {
            withContext(Dispatchers.Main) {
                callbackMAIN()
            }
        }
    }
}