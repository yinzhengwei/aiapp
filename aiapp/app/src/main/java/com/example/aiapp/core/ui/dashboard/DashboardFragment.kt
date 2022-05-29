package com.example.aiapp.core.ui.dashboard

import android.widget.TextView
import com.example.aiapp.base.BaseFragment
import com.example.aiapp.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {

    override fun layoutInflate() = FragmentDashboardBinding.inflate(inflater)

    override fun getViewModel() = DashboardViewModel::class.java

    override fun initView() {
    }

    override fun initData() {
        val textView: TextView = binding.textDashboard
        viewModel.text.observe(this) {
            textView.text = it
        }
        textView.setOnClickListener {
            //请求数据
            viewModel.loadData()
        }

    }


}