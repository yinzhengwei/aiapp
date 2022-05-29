package com.example.aiapp.core.ui.notifications

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.aiapp.R
import com.example.aiapp.base.BaseFragment
import com.example.aiapp.config.PathRouterUrl.PATH_WEBVIEW_ACTIVITY
import com.example.aiapp.databinding.FgChildLayoutBinding
import com.example.aiapp.util.ARouterUtil
import com.example.aiapp.util.initVertical
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
class ChildFragment : BaseFragment<FgChildLayoutBinding, NotificationsViewModel>() {

    private val adapter = ListAdapter()

    override fun layoutInflate() = FgChildLayoutBinding.inflate(layoutInflater)

    override fun getViewModel() = NotificationsViewModel::class.java

    override fun initView() {
        if (binding.recyclerView.childCount > 0) {
            return
        }
        context?.run {
            binding.recyclerView.initVertical(
                adapter,
                resources.getDimension(R.dimen.dp_1).toInt(),
                R.color.black_55
            )
        }

        initAdapter()

        viewModel.listDataLiveData.observe(this) {
            adapter.setNewData(it)

            binding.smartRefreshLayout.run {
                //结束刷新
                finishRefresh()
                //刷新不可用
                binding.smartRefreshLayout.isEnableRefresh = false
                //结束加载
                //binding.smartRefreshLayout.finishRefresh()
                //上拉加载不可用
                //binding.smartRefreshLayout.isEnableLoadMore = false
            }
        }

        initRefresh()
    }

    override fun initData() {
        if (binding.recyclerView.childCount > 0) {
            return
        }
        //发出网络请求
        arguments?.run {
            viewModel.loadListData(getInt("cid"))
        }
    }

    fun initAdapter() {
        //开启动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        //因为有些人不希望第一页看到动画，或者说希望前几个条目加载不需要有动画，所以可以设置不显示动画数量
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        //屏幕中最后一个可见子项的position
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        adapter.setNotDoAnimationCount(lastVisibleItem)

        adapter.setOnItemClickListener { _, _, position ->
            ARouterUtil.navigation(PATH_WEBVIEW_ACTIVITY, "link", adapter.data[position].link)
        }
    }

    private fun initRefresh() {
        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                initData()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
            }
        })

    }
}