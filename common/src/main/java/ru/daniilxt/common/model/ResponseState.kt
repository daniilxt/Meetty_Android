package ru.daniilxt.common.model

import ru.daniilxt.common.error.ErrorEntity

sealed class ResponseState {
    object Initial : ResponseState()
    object Progress : ResponseState()
    object Success : ResponseState()
    data class Error(val error: ErrorEntity) : ResponseState()
    data class Failure(val error: ResponseError) : ResponseState()
}
