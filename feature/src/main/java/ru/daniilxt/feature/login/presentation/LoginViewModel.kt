package ru.daniilxt.feature.login.presentation

import android.util.Patterns
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseState
import ru.daniilxt.common.token.TokenRepository
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.usecase.SignInUseCase

class LoginViewModel(
    private val router: FeatureRouter,
    private val signInUseCase: SignInUseCase,
    private val tokenRepository: TokenRepository
) : BaseViewModel() {
    fun openMainScreenFragment() {
        // router.openMainScreenFragment()
    }

    fun login(login: String, password: String) {
        setEventState(ResponseState.Progress)
        signInUseCase.invoke(login, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        tokenRepository.saveToken(it.data.accessToken)
                        tokenRepository.saveCurrentUserId(it.data.userId)
                        setEventState(ResponseState.Success)
                    }
                    is RequestResult.Error -> {
                        setEventState(ResponseState.Error(it.error))
                    }
                }
            }, {
            }).addTo(disposable)
    }

    fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isFieldCorrect(field: String) = field.isNotBlank()
}
