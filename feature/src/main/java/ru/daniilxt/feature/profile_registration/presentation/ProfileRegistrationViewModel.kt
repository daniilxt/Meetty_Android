package ru.daniilxt.feature.profile_registration.presentation

import android.util.Patterns
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import ru.daniilxt.feature.data_wrapper.UserCredentials

class ProfileRegistrationViewModel(
    private val dataWrapper: ProfileDataWrapper
) : BaseViewModel() {

    fun putRegistrationData(login: String, password: String) {
        dataWrapper.setUserCredentials(UserCredentials(login, password))
    }

    fun isPasswordsEqual(password: String, confirmPassword: String) =
        password.isNotBlank() && password == confirmPassword

    fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String, confirmPassword: String) =
        isPasswordCorrect(password) && isPasswordCorrect(confirmPassword)

    private fun isPasswordCorrect(password: String) =
        password.isNotEmpty() && password.length >= PASSWORD_LENGTH

    companion object {
        private const val PASSWORD_LENGTH = 6
    }
}
