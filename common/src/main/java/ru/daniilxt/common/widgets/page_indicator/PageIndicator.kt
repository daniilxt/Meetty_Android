package ru.daniilxt.common.widgets.page_indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import ru.daniilxt.common.R
import ru.daniilxt.common.extensions.dpToPx
import kotlin.math.max
import kotlin.math.min

class PageIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val DEFAULT_UNSELECTED_COLOR = Color.BLUE
    private val DEFAULT_SELECTED_COLOR = Color.BLUE
    private val DEFAULT_TEXT_COLOR = Color.WHITE
    private val DEFAULT_TEXT_SIZE = 12f
    private val DEFAULT_UNSELECTED_RADIUS = 4f
    private val DEFAULT_SELECTED_HEIGHT = 15f
    private val DEFAULT_SELECTED_WIDTH = 40f
    private val DEFAULT_SELECTED_CORNER_RADIUS = 15f
    private val DEFAULT_SELECTED_NODE_SPACING = 5f

    private lateinit var viewPager: ViewPager2
    private val unselectedPaint = Paint()
    private val selectedPaint = Paint()
    private val textPaint = Paint()
    private var currentPosition: Int = 0
    private var offset: Float = 0f
    private val titleTemplate: String by lazy {
        context.getString(R.string.tab_name)
    }
    private val textRect = Rect()

    private var unselectedRadius: Float
    private var selectedHeight: Float
    private var selectedWidth: Float
    private var selectedCornerRadius: Float
    private var nodeSpacing: Float
    private var textSize: Float

    private var count: Int = 0

    private lateinit var viewPagerCallback: ViewPager2.OnPageChangeCallback

    init {
        val attributesArray =
            context.obtainStyledAttributes(attrs, R.styleable.PageIndicator, defStyleAttr, 0)

        unselectedPaint.color = attributesArray.getColor(
            R.styleable.PageIndicator_unselectedColor,
            DEFAULT_UNSELECTED_COLOR
        )
        selectedPaint.color = attributesArray.getColor(
            R.styleable.PageIndicator_selectedColor,
            DEFAULT_SELECTED_COLOR
        )
        textPaint.color =
            attributesArray.getColor(R.styleable.PageIndicator_textColor, DEFAULT_TEXT_COLOR)
        textSize = attributesArray.getDimension(
            R.styleable.PageIndicator_textSize,
            context.dpToPx(DEFAULT_TEXT_SIZE)
        )
        textPaint.textSize = textSize
        val typefaceResourceId =
            attributesArray.getResourceId(R.styleable.PageIndicator_android_fontFamily, 0)
        if (typefaceResourceId != 0) {
            textPaint.typeface = ResourcesCompat.getFont(context, typefaceResourceId)
        }
        unselectedRadius = attributesArray.getDimension(
            R.styleable.PageIndicator_unselectedRadius,
            context.dpToPx(DEFAULT_UNSELECTED_RADIUS)
        )
        selectedHeight = attributesArray.getDimension(
            R.styleable.PageIndicator_selectedHeight,
            context.dpToPx(DEFAULT_SELECTED_HEIGHT)
        )
        selectedWidth = attributesArray.getDimension(
            R.styleable.PageIndicator_selectedWidth,
            context.dpToPx(DEFAULT_SELECTED_WIDTH)
        )
        selectedCornerRadius = attributesArray.getDimension(
            R.styleable.PageIndicator_selectedCornerRadius,
            context.dpToPx(DEFAULT_SELECTED_CORNER_RADIUS)
        )
        nodeSpacing = attributesArray.getDimension(
            R.styleable.PageIndicator_nodeSpacing,
            context.dpToPx(DEFAULT_SELECTED_NODE_SPACING)
        )

        attributesArray.recycle()
    }

    fun setViewPager(viewPager: ViewPager2) {
        this.viewPager = viewPager
        viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPosition = position
                offset = positionOffset
                invalidate()
            }
        }
        viewPager.registerOnPageChangeCallback(viewPagerCallback)
        count = viewPager.adapter?.itemCount ?: 0
    }

    override fun onDetachedFromWindow() {
        viewPager.unregisterOnPageChangeCallback(viewPagerCallback)
        super.onDetachedFromWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var lp: ViewGroup.LayoutParams? = layoutParams
        if (lp == null)
            lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        var width = calculateSize(suggestedMinimumWidth, lp.width, widthMeasureSpec)
        var height = calculateSize(suggestedMinimumHeight, lp.height, heightMeasureSpec)

        width += paddingLeft + paddingRight
        height += paddingTop + paddingBottom

        setMeasuredDimension(width, height)
    }

    private fun calculateSize(suggestedSize: Int, paramSize: Int, measureSpec: Int): Int {
        var result = 0
        val size = MeasureSpec.getSize(measureSpec)
        val mode = MeasureSpec.getMode(measureSpec)

        when (mode) {
            MeasureSpec.AT_MOST ->
                result = when (paramSize) {
                    ViewGroup.LayoutParams.WRAP_CONTENT -> min(suggestedSize, size)
                    ViewGroup.LayoutParams.MATCH_PARENT -> size
                    else -> min(paramSize, size)
                }
            MeasureSpec.EXACTLY -> result = size
            MeasureSpec.UNSPECIFIED ->
                result =
                    if (paramSize == ViewGroup.LayoutParams.WRAP_CONTENT ||
                        paramSize == ViewGroup.LayoutParams.MATCH_PARENT
                    )
                        suggestedSize
                    else {
                        paramSize
                    }
        }

        return result
    }

    override fun getSuggestedMinimumWidth(): Int {
        var suggested = super.getSuggestedMinimumHeight()
        if (count > 0) {
            suggested = Math.max(suggested, computeMaximumWidth())
        }
        return suggested
    }

    override fun getSuggestedMinimumHeight(): Int {
        var suggested = super.getSuggestedMinimumWidth()
        if (count > 0) {
            suggested = max(suggested, computeMaximumHeight())
        }
        return suggested
    }

    private fun computeMaximumHeight(): Int {
        return selectedHeight.toInt() * 2 + paddingTop + paddingBottom
    }

    private fun computeMaximumWidth(): Int {
        val totalSpacing = nodeSpacing * (count - 1)
        val unselectedWidth = unselectedRadius * 2 * (count)
        return (totalSpacing + unselectedWidth + selectedWidth).toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var x = 0f
        for (i in 0 until count) {
            if (i == currentPosition) {
                val h = 2 * unselectedRadius + selectedHeight * (1 - offset)
                val w = 2 * unselectedRadius + selectedWidth * (1 - offset)
                canvas?.drawRoundRect(
                    x,
                    height / 2f - h / 2,
                    x + w,
                    height / 2 + h / 2,
                    selectedCornerRadius,
                    selectedCornerRadius,
                    selectedPaint
                )
                // TODO change text to string array
                val text = String.format(titleTemplate, currentPosition + 1)
                textPaint.textSize = textSize * (1 - offset)
                textPaint.getTextBounds(text, 0, text.length, textRect)
                canvas?.drawText(
                    text,
                    x + w / 2 - textRect.width() / 2,
                    height / 2f + textRect.height() / 2,
                    textPaint
                )
                x += w + nodeSpacing
            } else if (i == currentPosition + 1) {
                val h = 2 * unselectedRadius + selectedHeight * offset
                val w = 2 * unselectedRadius + selectedWidth * offset
                canvas?.drawRoundRect(
                    x,
                    height / 2f - h / 2,
                    x + w,
                    height / 2 + h / 2,
                    selectedCornerRadius,
                    selectedCornerRadius,
                    unselectedPaint
                )
                val text = String.format(titleTemplate, currentPosition + 2)
                textPaint.textSize = textSize * offset
                textPaint.getTextBounds(text, 0, text.length, textRect)
                canvas?.drawText(
                    text,
                    x + w / 2 - textRect.width() / 2,
                    height / 2f + textRect.height() / 2,
                    textPaint
                )
                x += w + nodeSpacing
            } else {
                canvas?.drawCircle(
                    x + unselectedRadius,
                    height / 2f,
                    unselectedRadius,
                    unselectedPaint
                )
                x += 2 * unselectedRadius + nodeSpacing
            }
        }
    }
}
