package com.kunal.getmyparking.ui.dynamic_recycler_view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.roundToInt

class DynamicGridRecyclerView : RecyclerView {
    private var manager: GridLayoutManager? = null
    private var columnWidth = -1

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    fun getDynamicGridLayoutManager() = manager

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val attrsArray = intArrayOf(
                android.R.attr.columnWidth
            )
            val array = context.obtainStyledAttributes(attrs, attrsArray)
            columnWidth = array.getDimensionPixelSize(0, -1)
            array.recycle()
        }
        manager = CenteredGridLayoutManager(getContext(), 1)
        layoutManager = manager
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (columnWidth > 0) {
            val spanCount = max(2, measuredWidth / columnWidth)
            manager!!.spanCount = spanCount
        }
    }

    inner class CenteredGridLayoutManager(context: Context?, spanCount: Int) :
        GridLayoutManager(context, spanCount) {

        override fun getPaddingLeft(): Int {
            val totalItemWidth: Int = columnWidth * spanCount
            return if (totalItemWidth >= this@DynamicGridRecyclerView.measuredWidth) {
                super.getPaddingLeft() // do nothing
            } else {
                (this@DynamicGridRecyclerView.measuredWidth / (1f + spanCount) - totalItemWidth / (1f + spanCount)).roundToInt()
            }
        }

        override fun getPaddingRight(): Int {
            return paddingLeft
        }
    }
}