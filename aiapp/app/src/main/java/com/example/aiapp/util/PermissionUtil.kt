package com.example.aiapp.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Process
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.example.aiapp.core.launcher.CustomApplication

object PermissionUtil {

    fun hasPermissions(context: Context, vararg perms: String): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            return true
        }
        for (perm in perms) {
            if (context.checkPermission(
                    perm,
                    Process.myPid(),
                    Process.myUid()
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    fun requestNeededPermissions(activity: Activity?, permissions: String?) {
        requestPermissions(activity, arrayOf(permissions), 1)
    }

    fun requestNeededPermissions(activity: Activity?, permissions: Array<String?>?) {
        requestPermissions(activity, permissions, 1)
    }

    fun requestPermissions(activity: Activity?, permission: Array<String?>?, requestCode: Int) {
        ActivityCompat.requestPermissions(activity!!, permission!!, requestCode)
    }

    fun checkPermission(activity: Activity?, permission: String?): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity!!,
            permission!!
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 检查是否有sdcard权限
     */
    fun checkSdcardPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                CustomApplication.currentActivityCache?.startActivity(intent);
                false
            } else {
                true
            }
        } else {
            checkPermission(
                CustomApplication.currentActivityCache,
                Manifest.permission_group.STORAGE
            )
        }
    }
}