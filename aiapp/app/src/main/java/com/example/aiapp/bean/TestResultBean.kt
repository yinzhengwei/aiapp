package com.example.aiapp.bean

import android.os.Parcelable
import com.example.aiapp.base.BaseData
import kotlinx.parcelize.Parcelize

/**
 * @author yzw
 * @date 2022/5/26
 * @describe
 */
class TestResultBean(var data: MutableList<TestBean>) : BaseData() {
    @Parcelize
    class TestBean(
        var id: Int,
        var name: String
    ) : Parcelable
}
