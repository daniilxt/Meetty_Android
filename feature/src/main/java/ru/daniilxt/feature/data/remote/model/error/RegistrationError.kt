package ru.daniilxt.feature.data.remote.model.error

import ru.daniilxt.common.error.ErrorEntity

sealed class RegistrationError : ErrorEntity {
    object UserAlreadyExists : RegistrationError()
    object PasswordValidationFailed : RegistrationError()
    object WrongCode : RegistrationError()
    object Unknown : RegistrationError()
}
