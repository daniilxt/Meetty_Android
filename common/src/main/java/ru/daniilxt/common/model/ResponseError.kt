package ru.daniilxt.common.model

import androidx.annotation.StringRes
import ru.daniilxt.common.R
import ru.daniilxt.common.error.ErrorEntity

sealed class ResponseError(@StringRes val errResId: Int) : ErrorEntity {

    object ConnectionError : ResponseError(R.string.error_connection)
    object UnknownError : ResponseError(R.string.error_unknown)
}
