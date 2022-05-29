package com.example.aiapp.util

import com.alibaba.android.arouter.launcher.ARouter
import com.example.aiapp.BuildConfig
import com.example.aiapp.core.launcher.CustomApplication

/**
 * @author yzw
 * @date 2022/5/25
 * @describe
 */
object ThirdInitUtil {

    fun init(application: CustomApplication) {
        /*
         * ARouter路由
         */if (BuildConfig.DEBUG) {
            ARouter.openLog(); // 开启日志
            ARouter.openDebug(); // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
            ARouter.printStackTrace(); // 打印日志的时候打印线程堆栈
        }
        ARouter.init(application)

    }

}