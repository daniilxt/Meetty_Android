package ru.daniilxt.common.extensions

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.hardware.display.DisplayManagerCompat

fun Context.getStatusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Context.dpToPx(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

fun Context.getDrawableCompat(@DrawableRes drawable: Int) =
    ContextCompat.getDrawable(this, drawable)

fun Context.spToPx(sp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)

fun Context.screenValue(): DisplayMetrics {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val defaultDisplay =
            DisplayManagerCompat.getInstance(this).getDisplay(Display.DEFAULT_DISPLAY)
        val displayContext = this.createDisplayContext(defaultDisplay!!)
        displayContext.resources.displayMetrics
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        (this as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics
    }
}

val Context.actionBarSize
    get() = theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        .let { attrs -> attrs.getDimension(0, 0F).toInt().also { attrs.recycle() } }

val Context.statusBarHeightInPx
    get() = run {
        val resourceId = this.resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
        this.resources.getDimensionPixelSize(resourceId) / this.resources.displayMetrics.density
    }

val Context.navBarHeightInPx
    get() = run {
        val resourceId = this.resources.getIdentifier(
            "navigation_bar_height",
            "dimen",
            "android"
        )
        this.resources.getDimensionPixelSize(resourceId) / this.resources.displayMetrics.density
    }
