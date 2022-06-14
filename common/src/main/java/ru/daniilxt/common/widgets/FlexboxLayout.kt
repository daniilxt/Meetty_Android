package ru.daniilxt.common.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import ru.daniilxt.common.extensions.dpToPx

class FlexboxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private val leftViewMargin = context.dpToPx(10F).toInt()
    private val topViewMargin = context.dpToPx(8F).toInt()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var totalWidth = 0
        var totalHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(
                child, widthMeasureSpec, 0, heightMeasureSpec, totalHeight
            )
            val childMarginLeft = (child.layoutParams as MarginLayoutParams).leftMargin
            val childMarginRight = (child.layoutParams as MarginLayoutParams).rightMargin
            val childMarginTop = (child.layoutParams as MarginLayoutParams).marginStart
            val childMarginBottom = (child.layoutParams as MarginLayoutParams).bottomMargin

            totalWidth += child.measuredWidth + childMarginLeft + childMarginRight + leftViewMargin
            val widthMode = MeasureSpec.getMode(widthMeasureSpec)
            val widthSize = MeasureSpec.getSize(widthMeasureSpec)

            totalWidth = when (widthMode) {
                MeasureSpec.EXACTLY -> if (widthSize < totalWidth) {
                    totalHeight += child.measuredHeight + childMarginTop + childMarginBottom + topViewMargin
                    0
                } else totalWidth

                MeasureSpec.AT_MOST -> if (widthSize < totalWidth) {
                    totalHeight += child.measuredHeight + childMarginTop + childMarginBottom
                    0
                } else totalWidth
                MeasureSpec.UNSPECIFIED -> totalWidth
                else -> throw IllegalStateException()
            }
            totalHeight = maxOf(
                totalHeight,
                child.measuredHeight + childMarginTop + childMarginBottom + topViewMargin
            )
        }
        val resultWidth = resolveSize(totalWidth + paddingRight + paddingLeft, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentBottom = 0
        var currentEnd = 0
        var shift = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val marginLeft = (child.layoutParams as MarginLayoutParams).leftMargin
            val marginTop = (child.layoutParams as MarginLayoutParams).topMargin
            val marginRight = (child.layoutParams as MarginLayoutParams).rightMargin
            val marginBottom = (child.layoutParams as MarginLayoutParams).bottomMargin

            if (currentEnd + child.measuredWidth > width) {
                currentEnd = 0
                shift += currentBottom
            }
            child.layout(
                currentEnd + marginLeft + marginRight + leftViewMargin,
                shift + marginTop + marginBottom + topViewMargin,
                currentEnd + child.measuredWidth + marginLeft + marginRight + leftViewMargin,
                child.measuredHeight + shift + marginTop + marginBottom + topViewMargin
            )
            currentBottom = child.height + marginTop + marginBottom + topViewMargin
            currentEnd = child.right
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is MarginLayoutParams
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    companion object {
        private const val INNER_START_PADDING = 8F
    }
}
