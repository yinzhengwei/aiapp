package com.example.aiapp.core.common

import com.example.aiapp.R
import com.example.aiapp.base.BaseViewModel
import com.example.aiapp.core.ui.dashboard.DashboardFragment
import com.example.aiapp.core.ui.home.HomeFragment
import com.example.aiapp.core.ui.notifications.NotificationsFragment
import com.example.aiapp.util.LaunchUtil
import com.example.aiapp.widget.navigation.FragmentNavigator
import com.example.aiapp.widget.navigation.NavigationDestinationBean

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
class MainViewModel : BaseViewModel() {

    val liveData = createLiveData<MutableList<NavigationDestinationBean>>()

    fun loadNavigationData(fragmentNavigator: FragmentNavigator) {
        LaunchUtil.execute({
            mutableListOf<NavigationDestinationBean>().apply {
                val homeFragment = NavigationDestinationBean(
                    R.id.navigation_home,
                    fragmentNavigator,
                    HomeFragment::class.java,
                    R.string.title_home
                )
                add(homeFragment)

                val dashboardFragment = NavigationDestinationBean(
                    R.id.navigation_dashboard,
                    fragmentNavigator,
                    DashboardFragment::class.java,
                    R.string.title_dashboard
                )
                add(dashboardFragment)

                val notificationsFragment = NavigationDestinationBean(
                    R.id.navigation_notifications,
                    fragmentNavigator,
                    NotificationsFragment::class.java,
                    R.string.title_notifications
                )
                add(notificationsFragment)
            }
        }, {
            liveData.value = it
        })
    }

}