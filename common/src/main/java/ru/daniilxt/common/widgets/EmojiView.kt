package ru.daniilxt.common.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import ru.daniilxt.common.R
import ru.daniilxt.common.extensions.dpToPx
import ru.daniilxt.common.extensions.spToPx

class EmojiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    var text = ""
        set(value) {
            field = value
            requestLayout()
        }
    var textSize = context.spToPx(DEFAULT_TEXT_SIZE)
        set(value) {
            field = value
            requestLayout()
        }
    var reactionsCount = DEFAULT_REACTIONS_COUNT
        set(value) {
            field = value
            requestLayout()
        }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        textAlign = Paint.Align.LEFT
    }
    private val reactionsPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.RIGHT
    }

    private val textBounds = Rect()
    private val textCoordinate = PointF()

    private val tempFontMetrics = Paint.FontMetrics()

    private val reactionsBounds = Rect()
    private val reactionsCoordinate = PointF()

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.EmojiView,
            defStyleAttr,
            defStyleRes
        )

        text = typedArray.getString(R.styleable.EmojiView_customText).orEmpty()
        textPaint.color =
            typedArray.getColor(R.styleable.EmojiView_customTextColor, Color.RED)
        textPaint.textSize = typedArray.getDimension(
            R.styleable.EmojiView_customTextSize,
            textSize
        )
        reactionsCount =
            typedArray.getInteger(R.styleable.EmojiView_customReactionsCount, 0)
        reactionsPaint.textSize = textPaint.textSize
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(text, 0, text.length, textBounds)

        val textHeight = textBounds.height()
        val textWidth = textBounds.width()

        reactionsPaint.getTextBounds(
            reactionsCount.toString(),
            0,
            reactionsCount.toString().length,
            reactionsBounds
        )
        val reactionsHeight = reactionsBounds.height()
        val reactionsWidth = reactionsBounds.width()

        // view include inner vertical and inner horizontal
        // INNER_START_PADDING * 3 for spacing between emoji end text
        val totalWidth =
            textWidth + reactionsWidth + paddingRight + paddingLeft + context.dpToPx(
                INNER_START_PADDING * 3
            ).toInt()
        val totalHeight =
            maxOf(textHeight, reactionsHeight) + paddingTop + paddingBottom + context.dpToPx(
                INNER_TOP_PADDING * 2
            ).toInt()

        val resultWidth = resolveSize(totalWidth, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        textPaint.getFontMetrics(tempFontMetrics)
        textCoordinate.x = paddingStart.toFloat() + context.dpToPx(INNER_START_PADDING)
        textCoordinate.y = h / 2f + textBounds.height() / 2 - tempFontMetrics.descent

        reactionsPaint.getFontMetrics(tempFontMetrics)
        reactionsCoordinate.x =
            w.toFloat() - paddingEnd - context.dpToPx(INNER_END_PADDING) - tempFontMetrics.descent
        reactionsCoordinate.y = textCoordinate.y
    }

    var checked = false

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + SUPPORTED_DRAWABLE_STATE.size)
        if (isSelected) {
            mergeDrawableStates(drawableState, SUPPORTED_DRAWABLE_STATE)
            if (reactionsCount != 0 && !checked) {
                reactionsCount++
                checked = true
                requestLayout()
            }
        } else {
            if (reactionsCount != 0 && checked) {
                reactionsCount--
                checked = false
                requestLayout()
            }
        }
        return drawableState
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text, textCoordinate.x, textCoordinate.y, textPaint)
        canvas.drawText(
            reactionsCount.toString(),
            reactionsCoordinate.x,
            reactionsCoordinate.y,
            reactionsPaint
        )
    }

    companion object {
        private const val DEFAULT_TEXT_SIZE = 14F
        private val SUPPORTED_DRAWABLE_STATE = intArrayOf(android.R.attr.state_selected)
        private const val DEFAULT_REACTIONS_COUNT = 0
        private const val INNER_START_PADDING = 10F
        private val INNER_END_PADDING get() = INNER_START_PADDING
        private const val INNER_TOP_PADDING = 8F
    }
}
