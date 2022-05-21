package ru.daniilxt.common.model

sealed class ResponseState {
    object Initial : ResponseState()
    object Progress : ResponseState()
    object Success : ResponseState()
    data class Failure(val error: ResponseError) : ResponseState()
}
