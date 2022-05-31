package com.example.aiapp.util

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.aiapp.util.recyclerview.decoration.SpacesItemDecoration
import com.example.aiapp.util.recyclerview.decoration.SpacesItemLRDecoration
import com.example.aiapp.util.recyclerview.decoration.SpacesItemTBDecoration

/**
 * @author yzw
 * @date 2022/5/28
 * @describe 设置recyclerView通用属性
 */

//初始化水平列表
fun RecyclerView.initHorizontal(adapter: BaseQuickAdapter<*, BaseViewHolder>) {
    initLinear(adapter, LinearLayoutManager.HORIZONTAL, 0, 0)
}

//初始化水平条目带间距的列表
fun RecyclerView.initHorizontal(adapter: BaseQuickAdapter<*, BaseViewHolder>, space: Int) {
    initLinear(adapter, LinearLayoutManager.HORIZONTAL, space, 0)
}

//初始化水平条目带间距和间距颜色的列表
fun RecyclerView.initHorizontal(
    adapter: BaseQuickAdapter<*, BaseViewHolder>,
    space: Int,
    @DrawableRes drawableID: Int
) {
    initLinear(adapter, LinearLayoutManager.HORIZONTAL, space, drawableID)
}

//初始化垂直列表
fun RecyclerView.initVertical(adapter: BaseQuickAdapter<*, BaseViewHolder>) {
    initLinear(adapter, LinearLayoutManager.VERTICAL, 0, 0)
}

//初始化垂直条目带间距的列表
fun RecyclerView.initVertical(adapter: BaseQuickAdapter<*, BaseViewHolder>, space: Int) {
    initLinear(adapter, LinearLayoutManager.VERTICAL, space, 0)
}

//初始化垂直条目带间距和间距颜色的列表
fun RecyclerView.initVertical(
    adapter: BaseQuickAdapter<*, BaseViewHolder>,
    space: Int,
    @DrawableRes drawableID: Int
) {
    initLinear(adapter, LinearLayoutManager.VERTICAL, space, drawableID)
}

//没有间距的网格布局
fun RecyclerView.initGrid(adapter: BaseQuickAdapter<*, BaseViewHolder>, count: Int) {
    initGrid(adapter, 0, 0, count)
}

//四周间距相同的网格布局
fun RecyclerView.initGrid(
    adapter: BaseQuickAdapter<*, BaseViewHolder>,
    count: Int,
    spaceLRTB: Int
) {
    initGrid(adapter, spaceLRTB, spaceLRTB, count)
}

//上下、左右间距相同的网格布局
fun RecyclerView.initGrid(
    adapter: BaseQuickAdapter<*, BaseViewHolder>,
    spaceLR: Int,
    spaceTB: Int,
    count: Int
) {
    if (itemDecorationCount == 0) {
        addItemDecoration(SpacesItemDecoration(count, spaceLR, spaceTB))
    }
    if (layoutManager == null)
    //创建布局管理
        layoutManager = GridLayoutManager(context, count)

    this.adapter = adapter
}

private fun RecyclerView.initLinear(
    adapter: BaseQuickAdapter<*, BaseViewHolder>,
    orientation: Int,
    space: Int,
    @DrawableRes drawableID: Int
) {
    if (itemDecorationCount == 0) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            addItemDecoration(SpacesItemLRDecoration(space))
        } else {
            addItemDecoration(SpacesItemTBDecoration(context, space).apply {
                if (context != null && drawableID != 0) {
                    ContextCompat.getDrawable(context, drawableID)?.run {
                        setDrawable(this)
                    }
                }
            })
        }
    }
    if (layoutManager == null) {
        //创建布局管理
        layoutManager =
            LinearLayoutManager(context).apply { this.orientation = orientation }
    }

    //如果想要加快渲染速度并且不想复用条目的话，可以打开以下属性
    //recyclerView.setHasFixedSize(true);

    this.adapter = adapter
}


