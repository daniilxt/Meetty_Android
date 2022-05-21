package ru.daniilxt.common.error

import ru.daniilxt.common.model.ResponseError

sealed class RequestResult<T> {
    data class Success<T>(val data: T) : RequestResult<T>()
    data class Error<T>(val error: ResponseError) : RequestResult<T>()
}