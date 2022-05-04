package ru.daniilxt.feature.profile_personal_info.presentation

import android.content.Context
import androidx.viewbinding.ViewBinding

abstract class BaseDelegate(private val binding: ViewBinding) {
    val context: Context get() = binding.root.context
    open fun loadDelegate() {}
}
