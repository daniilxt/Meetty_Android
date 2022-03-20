package ru.daniilxt.common.model

import androidx.annotation.StringRes
import ru.daniilxt.common.R
import ru.daniilxt.common.error.ErrorEntity

sealed class EventError(@StringRes val errResId: Int) : ErrorEntity {

    object ConnectionError : EventError(R.string.error_connection)
    object UnknownError : EventError(R.string.error_unknown)
}
