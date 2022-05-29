package com.example.aiapp.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.aiapp.R
import com.example.aiapp.util.DialogUtil
import crossoverone.statuslib.StatusUtil
import kotlinx.coroutines.Job

/**
 * @author yzw
 * @date 2022/5/25
 * @describe
 */
abstract class BaseActivity<T : ViewBinding, X : BaseViewModel> : AppCompatActivity(), IBaseView {

    //lateinit表示延迟设置属性，数据类型必须是封装体
    lateinit var binding: T
    lateinit var viewModel: X
    var job: Job? = null

    /**
     * 加载布局
     *
     * 样例：AttySecondLayoutBinding.inflate(layoutInflater)
     */
    abstract fun layoutInflate(): T

    /**
     * 绑定model
     */
    abstract fun getViewModel(): Class<X>?

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = layoutInflate()
        setContentView(binding.root)

        bindLayout(getViewModel())
        initView()
        initData()
        setStatusBar()
    }

    protected open fun bindLayout(clzVM: Class<X>?) {
        clzVM?.run {
            viewModel = ViewModelProvider(this@BaseActivity)[this]
            viewModel.baseView = this@BaseActivity
        }
    }

    private fun setStatusBar() {
        //白色字体
        //StatusBarUtil.setDarkMode(this)
        //黑色字体
        //StatusBarUtil.setLightMode(this)
        ////设置状态栏为纯色
        //StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.translation))

        //第二个参数表示状态栏背景色，默认是白色
        StatusUtil.setUseStatusBarColor(this, ContextCompat.getColor(this, R.color.translation))
        //第二个参数表示状态栏是否沉浸式；第三个参数表示状态栏字体的颜色(true黑色；false白色)
        StatusUtil.setSystemStatus(this, true, true)
        setRootViewFitsSystemWindows(this,true)
    }

    /**
     * 代码实现android:fitsSystemWindows
     *
     * @param activity
     */
    fun setRootViewFitsSystemWindows(activity: Activity, fitSystemWindows: Boolean) {
        val winContent = activity.findViewById<View>(android.R.id.content) as ViewGroup
        if (winContent.childCount > 0) {
            val rootView = winContent.getChildAt(0) as ViewGroup
            rootView.fitsSystemWindows = fitSystemWindows
        }
    }

    //获取状态栏高度
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result;
    }


    override fun showLoading() {
        DialogUtil.showLoading(this)
    }

    override fun hiddenLoading() {
        DialogUtil.hiddenLoading()
    }

    override fun showLoginWindow() {
        DialogUtil.showLoginWindow(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.run {
            cancel()
        }
    }

}