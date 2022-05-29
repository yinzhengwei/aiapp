package com.example.aiapp.core.launcher

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap

/**
 * @author yzw
 * @date 2022/5/26
 * @describe 全局activity的活动状态
 */
class ActivityLifecycleCallback : ActivityLifecycleCallbacks {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        setCurrentActivityCache(p0 as AppCompatActivity)
    }

    override fun onActivityStarted(p0: Activity) {
        setCurrentActivityCache(p0 as AppCompatActivity)
    }

    override fun onActivityResumed(p0: Activity) {
        setCurrentActivityCache(p0 as AppCompatActivity)
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
        finishActivity(p0.javaClass)
    }

    /**
     * 打开一个activity的缓存处理
     *
     * @param activity 当前界面
     */
    @Synchronized
    fun setCurrentActivityCache(activity: AppCompatActivity) {
        CustomApplication.currentActivityCache = activity

        //将当前界面对象放入缓存中
        if (CustomApplication.activityCache == null) {
            CustomApplication.activityCache = ArrayMap<Class<*>, AppCompatActivity>()
        }
        CustomApplication.activityCache?.run {
            this[activity.javaClass] = activity
        }
    }

    /**
     * 关闭一个activity的缓存处理
     *
     * @param currentClx 当前界面
     */
    @Synchronized
    fun finishActivity(currentClx: Class<*>?) {
        CustomApplication.activityCache?.run {
            if (isEmpty)
                return@run
            //取出缓存
            get(currentClx)?.run {
                if (isFinishing) {
                    return
                }
                finish()
                remove(currentClx)
            }
        }
    }
}