package com.example.aiapp.util

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.aiapp.config.ParamsConstant

/**
 * Description：
 * Created by yzw.
 * Date: 5/24/22
 */
object ToastUtil {
    /**
     * toast默认的时间
     */
    private const val mDuration = 2000
    private var mToast: Toast? = null
    private val mHandler = Handler(Looper.getMainLooper())

    fun showToast(text: String) {
        showToast(text, mDuration)
    }

    fun showToast(resId: Int) {
        showToast(ParamsConstant.mContext.resources.getString(resId), mDuration)
    }

    fun showToast(resId: Int, duration: Int) {
        showToast(ParamsConstant.mContext.resources.getString(resId), duration)
    }

    fun showToast(text: String?, dur: Int) {
        mHandler.removeCallbacksAndMessages(null)
        if (mToast != null) {
            mToast!!.setText(text)
        } else {
            mToast = Toast.makeText(ParamsConstant.mContext, text, Toast.LENGTH_SHORT)
        }
        mToast!!.duration = dur
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mToast!!.show()
        } else {
            mHandler.post { mToast!!.show() }
        }
    }
}