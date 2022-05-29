package com.example.aiapp.core.ui.home

import android.text.InputType
import com.example.aiapp.base.BaseFragment
import com.example.aiapp.config.PathRouterUrl.PATH_SECOND_ACTIVITY
import com.example.aiapp.databinding.FragmentHomeBinding
import com.example.aiapp.util.ARouterUtil

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun layoutInflate() = FragmentHomeBinding.inflate(inflater)

    override fun getViewModel() = HomeViewModel::class.java

    override fun initView() {
    }

    override fun initData() {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textHome.text = it
        }

        binding.btn26.setOnClickListener {
            binding.edit.inputType =
                InputType.TYPE_TEXT_VARIATION_URI
        }
        binding.btn9.setOnClickListener {
            binding.edit.inputType = InputType.TYPE_CLASS_TEXT
        }
        binding.btnNum.setOnClickListener { binding.edit.inputType = InputType.TYPE_CLASS_NUMBER }
        binding.btnTest.setOnClickListener {
            ARouterUtil.navigation(PATH_SECOND_ACTIVITY)
        }
    }

}