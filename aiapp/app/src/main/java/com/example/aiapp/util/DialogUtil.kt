package com.example.aiapp.util

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.aiapp.R

/**
 * @author yzw
 * @date 2022/5/27
 * @describe
 */
object DialogUtil {

    private var dialog: Dialog? = null
    fun showLoading(context: Context) {
        hiddenLoading()
        try {
            dialog = AlertDialog.Builder(context).setCancelable(true)
                .setMessage(context.getString(R.string.loading_tip)).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hiddenLoading() {
        dialog?.run {
            if (isShowing)
                dismiss()
        }
    }

    fun showLoginWindow(context: Context) {
        try {
            AlertDialog.Builder(context).setCancelable(false)
                .setMessage(context.getString(R.string.please_login_tip))
                .setTitle(context.getString(R.string.un_login_tip))
                .setNegativeButton(context.getText(R.string.know_tip), null).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}