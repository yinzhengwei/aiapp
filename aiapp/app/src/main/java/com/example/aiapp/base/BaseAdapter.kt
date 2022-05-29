package com.example.aiapp.base

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author yzw
 * @date 2022/5/25
 *
 *
 * 基础adapter（所有的adapter统一继承该类）
 */
abstract class BaseAdapter<T>(@LayoutRes layoutResId: Int) :
    BaseQuickAdapter<T, BaseViewHolder>(layoutResId) {

    abstract fun loadLayout(): Int

    constructor() : this(0) {
        mLayoutResId = loadLayout()
    }

    abstract fun convertCallback(helper: BaseViewHolder, item: T)

    override fun convert(helper: BaseViewHolder, item: T) {
        convertCallback(helper, item)
    }
}