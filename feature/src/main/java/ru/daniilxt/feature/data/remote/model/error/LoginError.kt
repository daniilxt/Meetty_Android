package ru.daniilxt.feature.data.remote.model.error

import androidx.annotation.StringRes
import ru.daniilxt.common.error.ErrorEntity
import ru.daniilxt.feature.R

sealed class LoginError(@StringRes val errResId: Int) : ErrorEntity {
    object InvalidCredentials : LoginError(R.string.login_password_error)
    object Unknown : LoginError(R.string.error_unknown)
}
