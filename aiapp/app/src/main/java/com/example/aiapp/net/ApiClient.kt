package com.example.aiapp.net

import com.example.aiapp.net.manager.RetrofitManager

/**
 * @author yzw
 * @date 2022/5/25
 * @describe 构建接口访问的实体对象
 */
object ApiClient {

    //api 可自行扩展

    @JvmStatic
    //by :表示当前对象的'类型'委托给方法体中的实现逻辑返回; lazy：表示延迟加载，等待调用的时候在进行初始化
    val testApi by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        RetrofitManager.create(ITestApi::class.java)
    }

}