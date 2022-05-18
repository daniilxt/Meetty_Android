package ru.daniilxt.feature.login.presentation

import android.util.Patterns
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter

class LoginViewModel(private val router: FeatureRouter) : BaseViewModel() {
    fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun fieldCorrect(field: String) = field.isNotBlank()
    fun login(login: String, password: String) {
        router.openMainScreenFragment()
    }
}
