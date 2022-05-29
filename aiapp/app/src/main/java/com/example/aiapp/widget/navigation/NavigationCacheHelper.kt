@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.aiapp.widget.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import com.example.aiapp.R
import com.example.aiapp.core.launcher.CustomApplication
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 *
 *  此工具类主要完成导航器和对应子页面的关联属性的设置，这里放弃了 默认官方的使用方式，
 *  因为默认每次切花子页面的时候执行replace，销毁之前的页面，因此这里自定义了 FragmentNavigator 和 NavHostFragment属性类
 *  通过重写创建fragment时使用hide和show方法切换子页面，而不走之前的replace方式
 *
 *
 *  注意：在使用 BottomNavigationView + fragment的布局方式时：
 *  1、布局文件中的fragment一定要将属性name值替换成自定义的NavHostFragment:
 *   <fragment
 *      ...
 *       android:name="com.example.aiapp.widget.navigation.NavHostFragment"
 *      .../>
 *  2、fragment标签中一定要将属性navGraph去掉，不然就会走默认的导航设置
 *
 *
 */
object NavigationCacheHelper {

    /**
     * 设置导航器和对应子页面的关联属性
     */
    fun setNavigator(
        fragment: Fragment,
        navView: BottomNavigationView,
        list: MutableList<NavigationDestinationBean>
    ) {
        setNavigator(fragment, navView, list, 0)
    }

    fun setNavigator(
        fragment: Fragment,
        navView: BottomNavigationView,
        list: MutableList<NavigationDestinationBean>, defaultCheckIndex: Int
    ) {
//            默认官方的使用方式（但是这种方式会默认每次切花子页面的时候执行replace，销毁之前的页面）
//            val navController = findNavController(R.id.nav_fragment)
//            binding.navView.run {
//                setupWithNavController(navController)
//                isItemHorizontalTranslationEnabled = true
//            }

        val fragmentNavigator =
            FragmentNavigator(
                fragment.requireContext(),
                fragment.childFragmentManager,
                fragment.id
            )

        val navController = NavHostFragment.findNavController(fragment)

        //获取导航器提供者
        val provider = navController.navigatorProvider;
        //把自定义的Fragment导航器添加进去
        provider.addNavigator(fragmentNavigator);

        //手动创建导航图
        val navGraph = NavGraph(NavGraphNavigator(provider)).apply {
            //用自定义的导航器来创建目的地
            list.forEachIndexed { index, bean ->
                addDestination(bean.fragmentNavigator.createDestination().apply {
                    id = bean.idNavigation
                    className = bean.clazz.canonicalName
                    label = fragment.getString(bean.tabText)
                })

                //设置默认选中的tab
                if (index == defaultCheckIndex) {
                    setStartDestination(bean.idNavigation)
                    navView.menu.getItem(defaultCheckIndex).isChecked = true
                }
            }
        }

        //设置导航图
        navController.setGraph(navGraph, Bundle());

        //底部导航设置点击事件
        navView.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId);
            true
        }
    }


}