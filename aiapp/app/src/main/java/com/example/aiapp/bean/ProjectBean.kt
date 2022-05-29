package com.example.aiapp.bean

import android.os.Parcelable
import com.example.aiapp.base.BaseData
import kotlinx.parcelize.Parcelize

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
class ProjectBean(val data: ProjectDatasBean) : BaseData() {
    @Parcelize
   data class ProjectDatasBean(var datas: MutableList<ProjectItemBean>) : Parcelable

    @Parcelize
   data class ProjectItemBean(
        var desc: String,
        var envelopePic: String,
        var niceDate: String,
        var link: String,
        var title: String
    ) : Parcelable
}