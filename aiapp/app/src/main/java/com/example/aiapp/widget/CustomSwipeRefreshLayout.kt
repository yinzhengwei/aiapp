package com.example.aiapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class CustomSwipeRefreshLayout : SmartRefreshLayout {
    constructor(context: Context, attes: AttributeSet) : super(context, attes) {}
    constructor(context: Context) : super(context)

    private var startX = 0
    private var beginScrolll = false //是否开始滑动
    private var startY: Int = 0
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = Math.abs(endX - startX)
                val disY: Int = Math.abs(endY - startY)
                if (disX > disY) {
                    if (!beginScrolll)
                        parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    beginScrolll = true
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
                beginScrolll = false
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}

