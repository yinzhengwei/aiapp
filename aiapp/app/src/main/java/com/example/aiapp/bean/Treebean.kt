package com.example.aiapp.bean

import android.os.Parcelable
import com.example.aiapp.base.BaseData
import kotlinx.parcelize.Parcelize

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
class Treebean(var data: MutableList<TreeItemBean>) : BaseData() {
    @Parcelize
    class TreeItemBean(var id: Int, var name: String) : Parcelable
}