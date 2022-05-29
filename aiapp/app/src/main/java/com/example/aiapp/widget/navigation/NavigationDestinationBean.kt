package com.example.aiapp.widget.navigation

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
@Parcelize
class NavigationDestinationBean(
    val idNavigation: Int,
    val fragmentNavigator: FragmentNavigator,
    val clazz: Class<*>,
    @StringRes val tabText: Int
) : Parcelable