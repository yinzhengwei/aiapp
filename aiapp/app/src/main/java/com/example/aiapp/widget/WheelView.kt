package com.example.aiapp.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.example.aiapp.R
import com.example.aiapp.widget.WheelView
import kotlin.math.abs

class WheelView : ScrollView {
    //默认字体大小
    private var textSize = 0

    //默认距下，居左、居右距离
    private var paddingBottom = 0F
    private var paddingLeft = 0F
    private var paddingRight = 0F
    private var itemHeight = 0
    private var views: LinearLayout? = null
    private val items: MutableList<String> = ArrayList()

    /**
     * 设置显示界面上展现多少个item
     */
    var offset = OFF_SET_DEFAULT // 偏移量（需要在最前面和最后面补全）
    private var displayItemCount // 每页显示的数量
            = 0
    private var selectedIndex = 1
    private var initialY = 0
    private var scrollerTask: Runnable? = null
    private val newCheck = 50

    /**
     * 获取选中区域的边界
     */
    private var selectedAreaBorder: IntArray? = null
    private var paint: Paint? = null
    private var viewWidth = 0
    private var lineColor = 0
    var onWheelViewListener: OnWheelViewListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    class OnWheelViewListener {
        fun onSelected(selectedIndex: Int, item: String?) {}
    }

    fun getItemHeight(): Float {
        return textSize + paddingBottom
    }

    /**
     * 设置内容
     */
    fun setItems(list: List<String>?) {
        items.clear()
        items.addAll(list!!)

        // 前面和后面补全
        for (i in 0 until offset) {
            items.add(0, "")
            items.add("")
        }
        initData()
    }

    private fun init(context: Context) {
        textSize = context.resources.getDimension(R.dimen.dp_48).toInt()
        paddingRight = context.resources.getDimension(R.dimen.dp_32)
        paddingLeft = paddingRight
        paddingBottom = paddingLeft
        this.isVerticalScrollBarEnabled = false
        views = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }
        this.addView(views)
        scrollerTask = Runnable {
            val newY = scrollY
            if (initialY - newY == 0) { // stopped
                val remainder = initialY % itemHeight
                val divided = initialY / itemHeight
                if (remainder == 0) {
                    selectedIndex = divided + offset
                    onSelectedCallBack()
                } else {
                    if (remainder > itemHeight / 2) {
                        post {
                            smoothScrollTo(0, initialY - remainder + itemHeight)
                            selectedIndex = divided + offset + 1
                            onSelectedCallBack()
                        }
                    } else {
                        post {
                            smoothScrollTo(0, initialY - remainder)
                            selectedIndex = divided + offset
                            onSelectedCallBack()
                        }
                    }
                }
            } else {
                initialY = scrollY
                postDelayed(scrollerTask, newCheck.toLong())
            }
        }
    }

    private fun startScrollerTask() {
        initialY = scrollY
        postDelayed(scrollerTask, newCheck.toLong())
    }

    private fun initData() {
        displayItemCount = offset * 2 + 1
        views!!.removeAllViews()
        for (item in items) {
            views!!.addView(createView(item))
        }
        refreshItemView(0)
    }

    //获取数据
    fun getData(min: Int, max: Int): List<String> {
        val list = ArrayList<String>()
        if (min == 0 || max == 0) return list
        for (i in min..max) {
            if (i < 10) list.add("0$i") else list.add(i.toString())
        }
        return list
    }

    /**
     * 滚轮控件中的View 在这里创建， 本方法里面只是简单的TextView
     */
    private fun createView(item: String): TextView {
        val tv = TextView(context)
        itemHeight = (textSize + paddingBottom).toInt()
        tv.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, itemHeight)
        tv.isSingleLine = true
        tv.textSize = textSize.toFloat()
        tv.text = item
        tv.gravity = Gravity.CENTER
        views!!.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            itemHeight * displayItemCount
        )
        this.layoutParams.height = itemHeight * displayItemCount
        this.setPadding(paddingLeft.toInt(), 0, paddingRight.toInt(), 0)
        return tv
    }

    /**
     * 设置item文本大小
     *
     * @param textSize int 文本大小
     */
    fun setTextSize(textSize: Int) {
        this.textSize = textSize
    }

    /**
     * 设置文本之间间距，同时也设置了左右padding
     *
     * @param padding int
     */
    fun setPadding(padding: Int) {
        paddingRight = padding.toFloat()
        paddingLeft = padding.toFloat()
        paddingBottom = padding.toFloat()
    }

    /**
     * 设置文本之间间距，同时也设置了左右padding
     *
     * @param left   int
     * @param right  int
     * @param bottom int
     */
    fun setPadding(left: Int, right: Int, bottom: Int) {
        paddingBottom = bottom.toFloat()
        paddingLeft = left.toFloat()
        paddingRight = right.toFloat()
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        refreshItemView(t)

//        if (t > oldt) {
//            // Logger.d(TAG, "向下滚动");
//            scrollDirection = SCROLL_DIRECTION_DOWN;
//        } else {
//            // Logger.d(TAG, "向上滚动");
//            scrollDirection = SCROLL_DIRECTION_UP;
//        }
    }

    private fun refreshItemView(y: Int) {
        var position = y / itemHeight + offset
        val remainder = y % itemHeight
        val divided = y / itemHeight
        if (remainder == 0) {
            position = divided + offset
        } else {
            if (remainder > itemHeight / 2) {
                position = divided + offset + 1
            }
        }
        val childSize = views!!.childCount
        for (i in 0 until childSize) {
            val itemView = views!!.getChildAt(i) as TextView
            itemView.setTextColor(
                ContextCompat.getColor(
                    context!!, when {
                        position == i -> Color.WHITE
                        abs(position - i) == 1 -> R.color.white_60
                        else -> R.color.white_15
                    }
                )
            )
            itemView.setTextSize(
                TypedValue.COMPLEX_UNIT_SP, when {
                    position == i -> textSize.toFloat()
                    abs(position - i) == 1 -> 30f
                    else -> 26f
                }
            )
        }
    }

    private fun obtainSelectedAreaBorder(): IntArray {
        if (null == selectedAreaBorder) {
            selectedAreaBorder = IntArray(2)
            selectedAreaBorder!![0] = itemHeight * offset
            selectedAreaBorder!![1] = itemHeight * (offset + 1)
        }
        return selectedAreaBorder!!
    }

    //设置中间横行颜色（R.color.xxx）
    fun setLineColor(@ColorInt lineColor: Int) {
        this.lineColor = lineColor
    }

    override fun setBackground(bg: Drawable?) {
        if (viewWidth == 0) {
            viewWidth = (context as Activity).windowManager.defaultDisplay.width
        }
        if (null == paint) {
            paint = Paint().apply {
                color = ContextCompat.getColor(
                    context!!,
                    if (lineColor == 0) R.color.color_99BFFF_30 else lineColor
                )
                strokeWidth = 1f
            }
        }
        val background = object : Drawable() {
            override fun draw(canvas: Canvas) {
                canvas.drawLine(
                    0f,
                    obtainSelectedAreaBorder()[0].toFloat(),
                    viewWidth.toFloat(),
                    obtainSelectedAreaBorder()[0]
                        .toFloat(),
                    paint!!
                )
                canvas.drawLine(
                    0f,
                    obtainSelectedAreaBorder()[1].toFloat(),
                    viewWidth.toFloat(),
                    obtainSelectedAreaBorder()[1]
                        .toFloat(),
                    paint!!
                )
            }

            override fun setAlpha(alpha: Int) {}
            override fun setColorFilter(cf: ColorFilter?) {}
            override fun getOpacity(): Int {
                return PixelFormat.UNKNOWN
            }
        }
        super.setBackground(background)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        this.background = null
    }

    /**
     * 选中回调
     */
    private fun onSelectedCallBack() {
        val tempIndex = selectedIndex % items.size
        if (null != onWheelViewListener) {
            onWheelViewListener!!.onSelected(tempIndex - offset, items[tempIndex])
        }
    }

    fun setSelection(position: Int) {
        selectedIndex = position + offset
        refreshItemView(position * itemHeight)
        postDelayed({ smoothScrollTo(0, position * itemHeight) }, 60)
    }

    val seletedItem: String
        get() = items[selectedIndex]
    val seletedIndex: Int
        get() = selectedIndex - offset

    override fun fling(velocityY: Int) {
        super.fling(velocityY / 3)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            startScrollerTask()
        }
        return super.onTouchEvent(ev)
    }

    companion object {
        val TAG = WheelView::class.java.simpleName
        const val OFF_SET_DEFAULT = 1
        private const val SCROLL_DIRECTION_UP = 0
        private const val SCROLL_DIRECTION_DOWN = 1

        fun getViewMeasuredHeight(view: View): Int {
            calcViewMeasure(view)
            return view.measuredHeight
        }

        /**
         * 获取控件的宽度，如果获取的宽度为0，则重新计算尺寸后再返回宽度
         *
         * @param view
         * @return
         */
        fun getViewMeasuredWidth(view: View): Int {
            calcViewMeasure(view)
            return view.measuredWidth
        }

        /**
         * 测量控件的尺寸
         *
         * @param view
         */
        private fun calcViewMeasure(view: View) {
            val width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            val expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
            view.measure(width, expandSpec)
        }
    }
}