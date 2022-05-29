package com.example.aiapp.core.common

import com.example.aiapp.R
import com.example.aiapp.base.BaseActivity
import com.example.aiapp.databinding.ActivityMainBinding
import com.example.aiapp.widget.navigation.FragmentNavigator
import com.example.aiapp.widget.navigation.NavigationCacheHelper

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun layoutInflate() = ActivityMainBinding.inflate(layoutInflater)

    override fun getViewModel() = MainViewModel::class.java

    override fun initView() {
    }

    override fun initData() {
        //导航栏上方的子页展示区
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_fragment) ?: return

        //设置底部导航栏和子页面数据
        viewModel.liveData.observe(this) {
            NavigationCacheHelper.setNavigator(fragment, binding.navView, it, 2)
        }

        //加载底部导航栏属性对象数据
        viewModel.loadNavigationData(
            FragmentNavigator(
                fragment.requireContext(),
                fragment.childFragmentManager,
                fragment.id
            )
        )

    }
}