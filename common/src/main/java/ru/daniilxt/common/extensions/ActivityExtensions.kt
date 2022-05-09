package ru.daniilxt.common.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.view.ViewCompat

@Suppress("DEPRECATION")
fun Activity.setStatusBarColor(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = resources.getColor(color, theme)
    } else {
        window.statusBarColor = resources.getColor(color)
    }
}

@Suppress("DEPRECATION")
fun Activity.setNavigationBarColor(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.navigationBarColor = resources.getColor(color, theme)
    } else {
        window.navigationBarColor = resources.getColor(color)
    }
}

@Suppress("DEPRECATION")
fun Activity.isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}

typealias OnSystemInsetsChangedListener = (statusBarSize: Int, navigationBarSize: Int) -> Unit

/**
 * Util class for window insets
 */
object InsetUtil {

    /**
     * Removes system insets to make view hierarchy appear from edge to edge.
     *
     * @param view          view to remove insets
     * @param listener      listener that listens for updates top and bottom system insets of a view
     */
    fun removeSystemInsets(view: View, listener: OnSystemInsetsChangedListener) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->

            val desiredBottomInset = calculateDesiredBottomInset(
                view,
                insets.systemWindowInsetTop,
                insets.systemWindowInsetBottom,
                listener
            )

            ViewCompat.onApplyWindowInsets(
                view,
                insets.replaceSystemWindowInsets(0, 0, 0, desiredBottomInset)
            )
        }
    }

    /**
     * Calculates desired bottom inset in pixels to make NavigationBar transparent,
     * or to show the keyboard on the screen.
     *
     * @param view          view to calculate insets
     * @param topInset      system top inset in pixels
     * @param bottomInset   system bottom inset in pixels
     * @param listener      listener that listens for updates top and bottom system insets of a view
     */
    fun calculateDesiredBottomInset(
        view: View,
        topInset: Int,
        bottomInset: Int,
        listener: OnSystemInsetsChangedListener
    ): Int {
        val hasKeyboard = isKeyboardAppeared(view, bottomInset)
        val desiredBottomInset = if (hasKeyboard) bottomInset else 0
        listener(topInset, if (hasKeyboard) 0 else bottomInset)
        return desiredBottomInset
    }

    private fun isKeyboardAppeared(view: View, bottomInset: Int) =
        bottomInset / view.resources.displayMetrics.heightPixels.toDouble() > .25
}

fun Activity.setWindowTransparency(
    listener: OnSystemInsetsChangedListener = { _, _ -> }
) {
    InsetUtil.removeSystemInsets(window.decorView, listener)
    window.navigationBarColor = Color.TRANSPARENT
    window.statusBarColor = Color.TRANSPARENT
}
