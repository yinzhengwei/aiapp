package com.example.aiapp.core.ui.notifications

import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.example.aiapp.R
import com.example.aiapp.base.BaseAdapter
import com.example.aiapp.bean.ProjectBean
import com.example.aiapp.bean.TestResultBean
import com.example.aiapp.util.GsonUtil
import com.example.aiapp.util.ToastUtil
import com.example.aiapp.util.loadImage

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
class ListAdapter : BaseAdapter<ProjectBean.ProjectItemBean>() {

    override fun loadLayout() = R.layout.adapter_list_layout

    override fun convertCallback(helper: BaseViewHolder, item: ProjectBean.ProjectItemBean) {
        helper.run {
            setText(R.id.tv_title, item.title)
            setText(R.id.tv_desc, item.desc.trim())
            setText(R.id.tv_date, item.niceDate)

            getView<ImageView>(R.id.iv_pic).loadImage(item.envelopePic)
        }

    }
}