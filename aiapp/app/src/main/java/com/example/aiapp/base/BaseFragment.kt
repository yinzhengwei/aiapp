package com.example.aiapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.aiapp.util.DialogUtil

/**
 * @author yzw
 * @date 2022/5/25
 * @describe
 */
abstract class BaseFragment<T : ViewBinding, X : BaseViewModel> : Fragment(), IBaseView {

    lateinit var binding: T
    lateinit var viewModel: X

    /**
     * 加载布局
     *
     * 样例： FragmentBinding.inflate(inflater)
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

    lateinit var inflater: LayoutInflater

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.inflater = inflater

        bindLayout(getViewModel())
        binding = layoutInflate()
        initView()
        initData()

        return binding.root
    }

    override fun showLoading() {
        context?.run {
            DialogUtil.showLoading(this)
        }
    }

    override fun hiddenLoading() {
        DialogUtil.hiddenLoading()
    }

    override fun showLoginWindow() {
        context?.run {
            DialogUtil.showLoginWindow(this)
        }
    }

    protected open fun bindLayout(clzVM: Class<X>?) {
        clzVM?.run {
            viewModel = ViewModelProvider(this@BaseFragment)[this]
            viewModel.baseView = this@BaseFragment
        }
    }

}