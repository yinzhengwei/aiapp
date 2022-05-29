package com.example.aiapp.net.manager

import com.example.aiapp.config.ParamsConstant.BASE_URL
import com.example.aiapp.net.manager.OkHttpManager.OkClient
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

object RetrofitManager {

    //-------------------------------Retrofit-----------------------------------
    @JvmStatic//当前注解表示支持Java语音中直接调用实例
    //by :表示当前对象的'类型'委托给方法体中的实现逻辑; lazy：表示延迟加载，等待调用的时候在进行初始化
    private val retrofitClient: Retrofit by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            //此处写接口地址
            .baseUrl(BASE_URL)
            // 设置OkHttpclient
            .client(OkClient)
            // RxJava2
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            // 字符串
            .addConverterFactory(ScalarsConverterFactory.create())
            // Gson
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //构建接口实体
    fun <T> create(service: Class<T>): T {
        return retrofitClient.create(service)
    }

    //单张图片上传
    fun uploadSinglePhoto(path: String): MultipartBody.Part? {
        val file = File(path)
        if (file.exists()) {
            return MultipartBody.Part.createFormData(
                "files",
                file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )
        }
        return null
    }

    //多图片上传(在 retorfit 接口定义的参数中使用)
    fun uploadManyPhoto(list: List<String>): Array<MultipartBody.Part?>? {
        if (list.isEmpty()) {
            return null
        }
        val parts = arrayOfNulls<MultipartBody.Part?>(list.size)
        for (i in list.indices) {
            val file = File(list[i])
            if (file.exists()) {
                parts[i] = uploadSinglePhoto(file.absolutePath)
            }
        }
        return parts
    }
}