package ru.daniilxt.feature.user_profile.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseError
import ru.daniilxt.common.model.ResponseState
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.UserProfileInfo
import ru.daniilxt.feature.domain.usecase.GetProfileUserInfoUseCase
import timber.log.Timber

class UserProfileViewModel(
    private val router: FeatureRouter,
    private val gerUserProfileInfoUseCase: GetProfileUserInfoUseCase
) : BaseViewModel() {
    private val _userInfo: MutableStateFlow<List<UserProfileInfo>> = MutableStateFlow(
        emptyList()
    )
    val userIfo: StateFlow<List<UserProfileInfo>> get() = _userInfo
    fun back() {
        router.back()
    }

    fun loadUserProfile(isMy: Boolean, userId: Long) {
        setEventState(ResponseState.Progress)
        gerUserProfileInfoUseCase.invoke(isMy, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        Timber.i("!!!ERROR IS1 $it")
                        _userInfo.value = listOf(it.data)
                        setEventState(ResponseState.Success)
                    }
                    is RequestResult.Error -> {
                        Timber.i("!!!ERROR IS2 $it")
                        setEventState(ResponseState.Failure(it.error as ResponseError))
                    }
                }
            }, {
                Timber.i("!!!ERROR IS3 ${it.stackTrace} ${it.cause} ${it.message}")
                setEventState(ResponseState.Failure(ResponseError.ConnectionError))
            }).addTo(disposable)
    }
}
