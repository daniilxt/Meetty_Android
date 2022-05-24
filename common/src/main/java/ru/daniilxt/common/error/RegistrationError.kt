package ru.daniilxt.common.error

sealed class RegistrationError : ErrorEntity {
    object UserAlreadyExists : RegistrationError()
    object PasswordValidationFailed : RegistrationError()
    object WrongCode : RegistrationError()
    object Unknown : RegistrationError()
}
