package com.example.aiapp.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.aiapp.R


/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
class CustomBaseTitleView : LinearLayout {

    private var tv_title: TextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.run {
            init(this)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs?.run {
            init(this)
        }
    }

    fun init(attrs: AttributeSet) {
        val viewGroup = LayoutInflater.from(context).inflate(R.layout.view_base_title_layout, null)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.baseTitleView)
        val color = typedArray.getColor(R.styleable.baseTitleView_titleTextColor, Color.BLACK)
        val text = typedArray.getString(R.styleable.baseTitleView_titleText) ?: ""
        val textSize = typedArray.getInt(R.styleable.baseTitleView_titleTextSize, 18)

        tv_title = viewGroup.findViewById<TextView>(R.id.tv_title).apply {
            setText(text)
            setTextSize(textSize.toFloat())
            setTextColor(color)
        }
        viewGroup.findViewById<TextView>(R.id.tv_back).run {
            setOnClickListener {
                (context as Activity).finish()
            }
        }
        addView(viewGroup)
        typedArray.recycle()
    }

    fun setTitle(titleText: String) {
        tv_title?.run {
            text = titleText
        }
    }

}