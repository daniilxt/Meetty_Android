package ru.daniilxt.common.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import ru.daniilxt.common.R
import ru.daniilxt.common.extensions.dpToPx

class OutcomeMessageViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val topTitleMargin = context.dpToPx(8F).toInt()
    private val leftTitleMargin = context.dpToPx(22F).toInt()
    var drawable: Drawable? =
        ResourcesCompat.getDrawable(resources, R.drawable.bg_message_outcome, context.theme)
        set(value) {
                field = value
                requestLayout()
            }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var totalWidth = 0
        var totalHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(
                child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight
            )
            val childMarginLeft = (child.layoutParams as MarginLayoutParams).leftMargin
            val childMarginRight = (child.layoutParams as MarginLayoutParams).rightMargin
            val childMarginTop = (child.layoutParams as MarginLayoutParams).marginStart
            val childMarginBottom = (child.layoutParams as MarginLayoutParams).bottomMargin
            totalWidth += child.measuredWidth + childMarginLeft + childMarginRight
            totalHeight += child.measuredHeight + childMarginTop + childMarginBottom
        }
        val resultWidth = resolveSize(totalWidth + paddingRight + paddingLeft, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val messageText = getChildAt(0)
        val dateText = getChildAt(1)
        val flexboxLayout = getChildAt(2)

        messageText.layout(
            width - messageText.measuredWidth,
            topTitleMargin,
            width,
            messageText.measuredHeight + topTitleMargin
        )
        dateText.layout(
            messageText.right - dateText.measuredWidth - leftTitleMargin,
            messageText.bottom - topTitleMargin,
            messageText.right,
            dateText.measuredHeight + messageText.bottom
        )
        flexboxLayout.layout(
            width - flexboxLayout.measuredWidth,
            dateText.bottom - topTitleMargin / 2,
            width,
            flexboxLayout.measuredHeight + dateText.bottom
        )
    }

    override fun dispatchDraw(canvas: Canvas) {
        val message = getChildAt(0)
        val date = getChildAt(1)
        val drawableOval = drawable
        if (drawableOval != null) {
            drawableOval.setBounds(
                message.left - leftTitleMargin / 2,
                message.top - topTitleMargin,
                message.right,
                date.bottom - topTitleMargin
            )
            drawableOval.draw(canvas)
        }
        super.dispatchDraw(canvas)
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
}
