package ru.daniilxt.feature.main_screen.presentation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import ru.daniilxt.common.extensions.screenValue
import ru.daniilxt.feature.R
import timber.log.Timber

class BackdropViewAnimation(
    val context: Context,
    private val backdrop: View,
    private val bottomSheet: View,
    @DrawableRes private val openIcon: Int,
    @DrawableRes private val closeIcon: Int,
    @ColorRes private val colorIcon: Int
) {
    private val animatorSet = AnimatorSet()
    private val interpolator: Interpolator = AccelerateDecelerateInterpolator()

    private var buttonView: View? = null
    private var stateListener: StateListener? = null

    private var backdropShown: Boolean = false

    private val displayMetrics: DisplayMetrics = context.screenValue()

    fun addStateListener(stateListener: StateListener) {
        this.stateListener = stateListener
    }

    fun toggle(buttonView: View): ObjectAnimator {
        backdropShown = !backdropShown
        this.buttonView = buttonView
        updateIcon(buttonView)

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()
        var positionY: Int = backdrop.bottom - bottomSheet.top
        val backdropBottom: Int = backdrop.bottom + bottomSheet.top
        if (backdropBottom > displayMetrics.heightPixels && getActionBarSize() > 0) {
            positionY = displayMetrics.heightPixels - bottomSheet.top - getActionBarSize() * 3 / 4
        }

        val animator: ObjectAnimator =
            ObjectAnimator.ofFloat(
                bottomSheet,
                "translationY",
                if (backdropShown) (positionY / 4).toFloat() else 0F
            )
        animator.duration = 500
        animator.interpolator = interpolator
        animatorSet.play(animator)
        animator.start()

        if (backdropShown) stateListener?.onOpen(animator) else stateListener?.onClose(animator)

        return animator
    }

    private fun updateIcon(view: View) {
        if (view !is ImageView) {
            return
        }
        view.apply {
            setImageDrawable(
                ContextCompat.getDrawable(context, if (backdropShown) closeIcon else openIcon)
            )
            setColorFilter(
                ContextCompat.getColor(context, colorIcon), PorterDuff.Mode.SRC_IN
            )
        }
    }

    private fun getActionBarSize(): Int {
        val tv = TypedValue()
        var actionBarHeight = -1
        if (context.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.screenValue())
        }
        return actionBarHeight
    }

    companion object {
        private val TAG = BackdropViewAnimation::class.java.simpleName
    }
}

interface StateListener {
    fun onOpen(animator: ObjectAnimator?)
    fun onClose(animator: ObjectAnimator?)
}
