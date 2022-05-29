package com.example.aiapp.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yzw
 * @date 2022/3/21
 * @describe 设置recyclerView上下间距的辅助类
 */
class SpacesItemTBDecoration(context: Context?, private val space: Int) :
    DividerItemDecoration(context, VERTICAL) {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.run {
            if (layoutManager == null || adapter == null) {
                outRect.bottom = space
                return
            }

            //最后一个条目下方不加间距
            if (getChildLayoutPosition(view) != adapter!!.itemCount - 1)
                outRect.bottom = space
        }
    }
}