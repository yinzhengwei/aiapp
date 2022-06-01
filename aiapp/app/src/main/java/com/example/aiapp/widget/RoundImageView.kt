package com.example.aiapp.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.aiapp.R

class RoundImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var width = 0f
    private var height = 0f
    private var leftTopRadius = 0
    private var rightTopRadius = 0
    private var leftBottomRadius = 0
    private var rightBottomRadius = 0
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        width = getWidth().toFloat()
        height = getHeight().toFloat()
    }

    init {
        init(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun init(context: Context, attrs: AttributeSet?) {
        //读取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Round_Angle_Image_View)
        val defaultRadius = 10
        val radius = typedArray.getDimensionPixelOffset(
            R.styleable.Round_Angle_Image_View_radius,
            defaultRadius
        )
        leftTopRadius = typedArray.getDimensionPixelOffset(
            R.styleable.Round_Angle_Image_View_left_top_radius,
            defaultRadius
        )
        rightTopRadius = typedArray.getDimensionPixelOffset(
            R.styleable.Round_Angle_Image_View_right_top_radius,
            defaultRadius
        )
        leftBottomRadius = typedArray.getDimensionPixelOffset(
            R.styleable.Round_Angle_Image_View_left_bottom_radius,
            defaultRadius
        )
        rightBottomRadius = typedArray.getDimensionPixelOffset(
            R.styleable.Round_Angle_Image_View_right_bottom_radius,
            defaultRadius
        )

        //如果四个角的值没有设置，就用通用的radius
        if (leftTopRadius == defaultRadius) {
            leftTopRadius = radius
        }
        if (rightTopRadius == defaultRadius) {
            rightTopRadius = radius
        }
        if (leftBottomRadius == defaultRadius) {
            leftBottomRadius = radius
        }
        if (rightBottomRadius == defaultRadius) {
            rightBottomRadius = radius
        }
        typedArray.recycle()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val path = Path()
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        val rids = floatArrayOf(
            leftTopRadius.toFloat(),
            leftTopRadius.toFloat(),
            rightTopRadius.toFloat(),
            rightTopRadius.toFloat(),
            leftBottomRadius.toFloat(),
            leftBottomRadius.toFloat(),
            rightBottomRadius.toFloat(),
            rightBottomRadius.toFloat()
        )
        path.addRoundRect(RectF(0F, 0F, width, height), rids, Path.Direction.CW)
        canvas.clipPath(path)
        super.onDraw(canvas)
    }

}