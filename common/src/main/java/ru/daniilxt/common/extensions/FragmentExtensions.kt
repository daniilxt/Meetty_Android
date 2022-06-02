package ru.daniilxt.common.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Fragment.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Fragment.addBackPressedCallback(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action()
            }
        }
    )
}

/**
 * Allows hide keyboard and do something after delay. Used coroutine
 *
 * @param delayDuration The duration of delay
 * @param callback Something will invoked after delay
 * */

fun Fragment.hideKeyboardWithDelay(delayDuration: Long = 100, callback: () -> Unit) {
    this.lifecycleScope.launch {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)
        delay(delayDuration)
        callback.invoke()
    }
}

fun FragmentManager.showDialog(dialogFragment: DialogFragment) {
    dialogFragment.show(this, dialogFragment::class.java.simpleName)
}
