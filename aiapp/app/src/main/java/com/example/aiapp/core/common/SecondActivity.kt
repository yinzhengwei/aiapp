package com.example.aiapp.core.common

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.aiapp.base.BaseActivity
import com.example.aiapp.config.PathRouterUrl.PATH_SECOND_ACTIVITY
import com.example.aiapp.core.ui.home.HomeViewModel
import com.example.aiapp.databinding.AttySecondLayoutBinding
import com.example.aiapp.util.CalendarUtil
import com.example.aiapp.util.GsonUtil
import com.example.aiapp.util.ToastUtil
import com.example.aiapp.util.loadImage

/**
 * @author yzw
 * @date 2022/5/25
 * @describe
 */
@Route(path = PATH_SECOND_ACTIVITY)
class SecondActivity : BaseActivity<AttySecondLayoutBinding, HomeViewModel>() {

    override fun layoutInflate() = AttySecondLayoutBinding.inflate(layoutInflater)

    override fun getViewModel() = HomeViewModel::class.java

    override fun initView() {
    }

    override fun initData() {
        binding.iv.loadImage("https://img-blog.csdnimg.cn/20201014180756925.png")

        binding.tv.setOnClickListener {
            CalendarUtil.addCalendarEventRemind(this, "元艺说", "您订阅的数字藏品开始抢购啦",
                System.currentTimeMillis() + 5000, System.currentTimeMillis() + 20000, 0, object :
                    CalendarUtil.onCalendarRemindListener {
                    override fun onFailed(error_code: CalendarUtil.onCalendarRemindListener.Status?) {
                        ToastUtil.showToast("onFailed")
                    }

                    override fun onSuccess() {
                        ToastUtil.showToast("onSuccess")
                    }
                })
        }

    }
}