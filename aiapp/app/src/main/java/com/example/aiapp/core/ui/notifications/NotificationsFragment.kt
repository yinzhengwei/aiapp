package com.example.aiapp.core.ui.notifications

import androidx.fragment.app.Fragment
import com.example.aiapp.base.BaseFragment
import com.example.aiapp.bean.Treebean
import com.example.aiapp.databinding.FragmentNotificationsBinding
import com.google.android.material.tabs.TabLayoutMediator

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding, NotificationsViewModel>() {

    private var fragmentList: MutableList<Fragment> = ArrayList()

    override fun layoutInflate() = FragmentNotificationsBinding.inflate(inflater)

    override fun getViewModel() = NotificationsViewModel::class.java

    override fun initView() {
    }

    override fun initData() {
        viewModel.treeDataLiveData.observe(this) {
            createTab(it)
        }

        //获取导航栏数据
        viewModel.loadTreeData()
    }

    private fun createTab(list: MutableList<Treebean.TreeItemBean>) {
        // 根据获取到的tab数量添加子页面数量
        list.forEach { _ ->
            fragmentList.add(ChildFragment())
        }
        binding.viewPager.adapter = SectionPagerAdapter(this, list, fragmentList)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = list[position].name
        }.attach()
    }

}