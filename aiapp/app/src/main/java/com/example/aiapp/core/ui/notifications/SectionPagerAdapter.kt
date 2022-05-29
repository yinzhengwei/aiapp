package com.example.aiapp.core.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aiapp.bean.Treebean

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
class SectionPagerAdapter(
    fragment: Fragment,
    val mutableList: MutableList<Treebean.TreeItemBean>,
    val list: MutableList<Fragment>
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = list.size

    override fun createFragment(position: Int) = list[position].apply {
        arguments = Bundle().apply { putInt("cid", mutableList[position].id) }
    }

}