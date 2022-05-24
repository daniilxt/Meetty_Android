package ru.daniilxt.feature.data.remote.model.error

import ru.daniilxt.common.error.ErrorEntity

sealed class LoginError : ErrorEntity {
    object NotActivatedAccount : LoginError()
    object InvalidCredentials : LoginError()
    object Unknown : LoginError()
    object PushesFailed : LoginError()
}
