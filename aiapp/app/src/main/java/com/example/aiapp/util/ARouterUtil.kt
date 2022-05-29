package com.example.aiapp.util

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author yzw
 * @date 2022/3/17
 * @describe 路由器管理类
 *
 * 官网文档：https://github.com/alibaba/ARouter/blob/master/README_CN.md
 */
object ARouterUtil {

    /**
     * 设置反射数据的加载
     */
    fun inject(clazz: Class<AppCompatActivity>) {
        ARouter.getInstance().inject(clazz)
    }

    /**
     * 普通不带参数跳转
     *
     * @param path 跳转路径
     */
    fun navigation(path: String?) {
        ARouter.getInstance().build(path).navigation()
    }

    /**
     * 普通不带参数制定FLAG的跳转
     *
     * @param path 跳转路径
     */
    fun navigation(path: String?, flag: Int) {
        ARouter.getInstance().build(path).withFlags(flag).navigation()
    }

    /**
     * 普通不带参数有回调当前activity的activityForResult方法
     *
     * @param path 跳转路径
     */
    fun navigation(path: String?, context: Activity?, requestCode: Int) {
        ARouter.getInstance().build(path).navigation(context, requestCode)
    }

    /**
     * 带参数跳转
     *
     * @param path 跳转路径
     */
    fun navigation(path: String?, key: String?, value: String?) {
        ARouter.getInstance().build(path).withString(key, value).navigation()
    }

//    /**
//     * 转场动画(常规方式)
//     *
//     * @param path 跳转路径
//     */
//    fun navigation(path: String) {
//        ARouter.getInstance()
//            .build("/test/activity2")
//            .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
//            .navigation(this);
//    }

    /**
     * 带参数跳转
     *
     * @param path 跳转路径
     */
    fun navigation(path: String?, key: String?, value: Int) {
        ARouter.getInstance().build(path).withInt(key, value).navigation()
    }

    /**
     * 带多个参数跳转
     *
     * @param path 跳转路径
     */
    fun navigation(path: String?, paramsBundle: Bundle?) {
        ARouter.getInstance().build(path).apply {
            if (paramsBundle != null)
                with(paramsBundle)
        }.navigation()
    }

    /**
     * 带多个参数跳转并且有回调当前activity的activityForResult方法
     *
     * @param path 跳转路径
     */
    fun navigation(path: String?, paramsBundle: Bundle?, context: Activity?, requestCode: Int) {
        ARouter.getInstance().build(path).apply {
            if (paramsBundle != null)
                with(paramsBundle)
        }.navigation(context, requestCode)
    }
}