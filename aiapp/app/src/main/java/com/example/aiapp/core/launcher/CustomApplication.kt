package com.example.aiapp.core.launcher

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import com.example.aiapp.config.ParamsConstant
import com.example.aiapp.util.ThirdInitUtil

/**
 * @author yzw
 * @date 2022/5/24
 * @describe
 */
class CustomApplication : Application() {

    companion object {
        //当前展示的activity
        var currentActivityCache: AppCompatActivity? = null

        //当前所有已经打开且未关闭的acitivty
        var activityCache: ArrayMap<Class<*>, AppCompatActivity>? = null
    }

    override fun onCreate() {
        super.onCreate()

        ParamsConstant.mContext = this
        registerActivityLifecycleCallbacks(ActivityLifecycleCallback())
        ThirdInitUtil.init(this)
    }

}