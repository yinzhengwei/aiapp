package com.example.aiapp.net.manager

import com.example.aiapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @author yzw
 * @date 2022/5/25
 * @describe OkHttp定义
 */
object OkHttpManager {

    private val CONNECT_TIMEOUT = 20L
    private val READ_TIMEOUT = 20L
    private val WRITE_TIMEOUT = 10L

    //---------------------------------OkHttp-------------------------------
    @JvmStatic//当前注解表示支持Java语音中直接调用实例
    //by :表示当前对象的'类型'委托给方法体中的实现逻辑; lazy：表示延迟加载，等待调用的时候在进行初始化
    val OkClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            //log拦截器
            builder.addInterceptor(loggingInterceptor)
        }
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        builder.build()
    }

    /**
     * 日志interceptor
     */
    private val loggingInterceptor: Interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}