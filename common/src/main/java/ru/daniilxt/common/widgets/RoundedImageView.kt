package ru.daniilxt.common.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import ru.daniilxt.common.R

class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttrs) {

    init {
        clipToOutline = true
        setBackgroundResource(R.drawable.bg_circle)
        scaleType = ScaleType.CENTER_CROP
    }
}