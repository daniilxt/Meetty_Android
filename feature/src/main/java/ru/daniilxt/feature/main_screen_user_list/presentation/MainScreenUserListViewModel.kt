package ru.daniilxt.feature.main_screen_user_list.presentation

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseState
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.UserCard
import ru.daniilxt.feature.domain.model.toUserCard
import ru.daniilxt.feature.domain.usecase.GetUsersInfoUseCase
import timber.log.Timber

@SuppressLint("NewApi")
class MainScreenUserListViewModel(
    private val router: FeatureRouter,
    private val getUsersInfoUseCase: GetUsersInfoUseCase
) : BaseViewModel() {

    private val _userCards: MutableStateFlow<List<UserCard>> = MutableStateFlow(emptyList())
    val userCards: StateFlow<List<UserCard>> get() = _userCards

    init {
        loadUsers()
    }

    fun loadUsers() {
        setEventState(ResponseState.Progress)
        getUsersInfoUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        _userCards.value = it.data.map { item -> item.toUserCard() }
                    }
                    is RequestResult.Error -> {
                    }
                }
            }, {
                Timber.i("??? data $it")
            }).addTo(disposable)
    }
}
