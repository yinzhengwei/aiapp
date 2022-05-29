package com.example.aiapp.core.ui.notifications

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.aiapp.R
import com.example.aiapp.base.BaseFragment
import com.example.aiapp.bean.ProjectBean
import com.example.aiapp.config.PathRouterUrl.PATH_WEBVIEW_ACTIVITY
import com.example.aiapp.databinding.FgChildLayoutBinding
import com.example.aiapp.util.ARouterUtil
import com.example.aiapp.util.initVertical

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
class ChildFragment : BaseFragment<FgChildLayoutBinding, NotificationsViewModel>() {

    private val adapter = ListAdapter()
//    出现了头布局就不会显示Empty
//    adapter.setHeaderAndEmpty(true);
//    adapter.setHeaderFooterEmpty(true,true);

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
            adapter.isUpFetching = false
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

    fun initAdapter(){
        //开启动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        //默认动画只执行一次，要想多次执行开启下方代码
        //adapter.isFirstOnly(false);

        //因为有些人不希望第一页看到动画，或者说希望前几个条目加载不需要有动画，所以可以设置不显示动画数量
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        //屏幕中最后一个可见子项的position
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        adapter.setNotDoAnimationCount(lastVisibleItem)

        adapter.setOnItemClickListener { adapter, view, position ->
            val projectItemBean = adapter.data[position] as ProjectBean.ProjectItemBean
            ARouterUtil.navigation(PATH_WEBVIEW_ACTIVITY, "link", projectItemBean.link)
        }
    }

    private fun initRefresh() {
        //允许下拉
        adapter.isUpFetchEnable = true;
        //下拉监听
        adapter.setUpFetchListener {
            initData()
        }
        adapter.setEnableLoadMore(true)
        adapter.setOnLoadMoreListener({

        }, binding.recyclerView)
    }
}