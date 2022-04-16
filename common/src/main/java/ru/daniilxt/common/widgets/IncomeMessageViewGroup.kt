package ru.daniilxt.common.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import ru.daniilxt.common.R
import ru.daniilxt.common.extensions.dpToPx

class IncomeMessageViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val leftMargin = context.dpToPx(22F).toInt()
    private val flexBoxTopMargin = context.dpToPx(10F).toInt()
    private val topTitleMargin = context.dpToPx(8F).toInt()
    private val leftTitleMargin = context.dpToPx(22F).toInt()
    var drawable: Drawable? =
        ResourcesCompat.getDrawable(resources, R.drawable.bg_message, context.theme)
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
        val imageView = getChildAt(0)
        val titleText = getChildAt(1)
        val messageText = getChildAt(2)
        val flexboxLayout = getChildAt(3)

        imageView.layout(
            0, 0, imageView.measuredWidth, imageView.measuredHeight
        )
        titleText.layout(
            imageView.width + leftMargin,
            topTitleMargin,
            titleText.measuredWidth + leftTitleMargin + imageView.right,
            titleText.measuredHeight + topTitleMargin
        )
        messageText.layout(
            titleText.left,
            titleText.bottom,
            messageText.measuredWidth + titleText.left,
            messageText.measuredHeight + titleText.bottom
        )
        flexboxLayout.layout(
            messageText.left - leftTitleMargin,
            messageText.bottom + flexBoxTopMargin,
            flexboxLayout.measuredWidth + messageText.left - leftTitleMargin,
            flexboxLayout.measuredHeight + messageText.bottom + flexBoxTopMargin
        )
    }

    override fun dispatchDraw(canvas: Canvas) {

        val title = getChildAt(1)
        val message = getChildAt(2)
        val rectWidth = maxViewWidth(title, message)
        val drawableOval = drawable
        if (drawableOval != null) {
            drawableOval.setBounds(
                title.left - leftTitleMargin / 2,
                title.top - topTitleMargin,
                rectWidth.right,
                message.bottom + topTitleMargin
            )
            drawableOval.draw(canvas)
        }
        super.dispatchDraw(canvas)
    }

    private fun maxViewWidth(title: View, message: View): View {
        return if (title.width > message.width) {
            title
        } else {
            message
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
}
