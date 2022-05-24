package ru.daniilxt.common.extensions

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ru.daniilxt.common.R
import ru.daniilxt.common.databinding.InputFieldBinding
import ru.daniilxt.common.utils.DebounceClickListener
import ru.daniilxt.common.utils.DebouncePostHandler
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.util.*

@Suppress("DEPRECATION")
fun View.setLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            var flags = systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            systemUiVisibility = flags
        }
    }
}

@Suppress("DEPRECATION")
fun View.clearLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowInsetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            var flags = systemUiVisibility
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            systemUiVisibility = flags
        }
    }
}

fun View.setDebounceClickListener(
    delay: Long = DebouncePostHandler.DEFAULT_DELAY,
    onClickListener: View.OnClickListener
) {
    setOnClickListener(DebounceClickListener(delay, onClickListener))
}

@SuppressLint("ResourceAsColor")
fun TextView.setColoredLastWord(
    @StringRes resId: Int,
    @ColorRes colorId: Int
) {
    val str = resources.getString(resId)
    val startIndex = str.indexOfLast { it == ' ' } + 1
    val endIndex = str.length
    val spannable = SpannableString(str)
    spannable.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(context, colorId)),
        startIndex,
        endIndex,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = spannable
}

fun TextView.setResourceString(
    @StringRes id: Int
) {
    this.text = resources.getString(id)
}

fun TextView.setResourceColor(@ColorRes resId: Int) {
    val color = ContextCompat.getColor(context, resId)
    this.setTextColor(color)
}

fun View.rotateView(startAngle: Float = 180f, endAngle: Float = 0f, duration: Long = 300) {
    val rotate = ObjectAnimator.ofFloat(this, "rotation", startAngle, endAngle)
    rotate.duration = duration
    rotate.start()
}

fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this).toInt() }
        top?.run { topMargin = dpToPx(this).toInt() }
        right?.run { rightMargin = dpToPx(this).toInt() }
        bottom?.run { bottomMargin = dpToPx(this).toInt() }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Float = context.dpToPx(dp)

/**
 * Shows the animated text.
 *
 * @param text Displayed text.
 */
fun TextView.showAnimatedText(text: String) {
    val anim1 = AnimationUtils.loadAnimation(this.context, R.anim.appear_text)
    this.startAnimation(anim1)
    this.text = text
}

/**
 * Allows set text attributes on EditTextLayout. Set hint,text,input type
 *
 * @param hintText The hint text string
 * @param textLength The constraint of text. Like: max text length is 6
 * @param inputType The type of text like Password,TextAllCaps etc..
 * @param text The text in EditText..
 * @param textTypeface The text typeface like BOLD or NORMAL etc...
 */
fun InputFieldBinding.setInputFormAttributes(
    hintText: String,
    textLength: Int = 100,
    inputType: Int = (InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS),
    text: String = "",
    textTypeface: Int = Typeface.NORMAL
) {
    with(this) {
        textInputLayout.hint = hintText
        textInputEt.inputType = inputType
        textInputLayout.typeface =
            ResourcesCompat.getFont(this.textInputEt.context, R.font.gilroy_medium)
        textInputEt.filters += InputFilter.LengthFilter(textLength)
        textInputEt.setText(text)
        textInputEt.setTypeface(textInputEt.typeface, textTypeface)
    }
}

/**
 * This extension set the capitalized name of the month in STANDALONE format.
 * example: July | Июль
 *
 * @param yearMonth The date in the year month format
 * @param text The text after month string
 */
@SuppressLint("NewApi")
fun TextView.setStandaloneMonthString(yearMonth: YearMonth, text: String = "") {
    val calendar = Calendar.getInstance()
    calendar.set(
        yearMonth.year,
        yearMonth.monthValue - 1,
        yearMonth.atDay(1).dayOfMonth
    )
    val month = android.text.format.DateFormat.format("LLLL", calendar).toString()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    this.text = context.getString(R.string.calendar_month_header, month, text)
}

fun TextView.setTextColorFromRes(@ColorRes resId: Int) {
    val color = ContextCompat.getColor(context, resId)
    this.setTextColor(color)
}

fun Month.getStandaloneName(): String {
    return SimpleDateFormat("LLLL", Locale.getDefault()).format(
        LocalDate.now().withMonth(this.value).toDate()
    )
}
