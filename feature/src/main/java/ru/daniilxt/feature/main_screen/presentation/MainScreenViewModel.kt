package ru.daniilxt.feature.main_screen.presentation

import android.annotation.SuppressLint
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
import ru.daniilxt.feature.navigation.interfaces.MainScreenRouter

@SuppressLint("NewApi")
class MainScreenViewModel(
    private val router: FeatureRouter,
    private val mainScreenRouter: MainScreenRouter,
    private val gerUserProfileInfoUseCase: GetProfileUserInfoUseCase
) : BaseViewModel() {
    private val _userInfo: MutableStateFlow<List<UserProfileInfo>> = MutableStateFlow(
        emptyList()
    )
    val userIfo: StateFlow<List<UserProfileInfo>> get() = _userInfo

    init {
        loadUserProfile(true, -1)
    }

    fun openMapFragment() {
        mainScreenRouter.openMainScreenMapFragment()
    }

    fun openMainScreenUserCardFragment() {
        mainScreenRouter.openMainScreenUserCardFragment()
    }

    fun openUserListFragment() {
        mainScreenRouter.openMainScreenUserListFragment()
    }

    fun openUserProfile(isMy: Boolean, userId: Long) {
        router.openUserProfile(isMy, userId)
    }

    private fun loadUserProfile(isMy: Boolean, userId: Long) {
        setEventState(ResponseState.Progress)
        gerUserProfileInfoUseCase.invoke(isMy, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        setEventState(ResponseState.Success)
                        _userInfo.value = listOf(it.data)
                    }
                    is RequestResult.Error -> {
                        setEventState(ResponseState.Failure(it.error as ResponseError))
                    }
                }
            }, {
                setEventState(ResponseState.Failure(ResponseError.ConnectionError))
            }).addTo(disposable)
    }
}
