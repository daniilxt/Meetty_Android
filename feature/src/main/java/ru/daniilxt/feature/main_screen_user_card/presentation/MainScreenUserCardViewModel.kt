package ru.daniilxt.feature.main_screen_user_card.presentation

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
import ru.daniilxt.feature.domain.model.SwipedUserCard
import ru.daniilxt.feature.domain.model.toSwipesUserCard
import ru.daniilxt.feature.domain.usecase.GetUsersInfoUseCase
import timber.log.Timber

class MainScreenUserCardViewModel(
    private val router: FeatureRouter,
    private val getUsersInfoUseCase: GetUsersInfoUseCase
) : BaseViewModel() {

    private val _userCards: MutableStateFlow<List<SwipedUserCard>> = MutableStateFlow(emptyList())
    val userCards: StateFlow<List<SwipedUserCard>> get() = _userCards

    fun getUsers() {
        setEventState(ResponseState.Progress)
        getUsersInfoUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        _userCards.value = it.data.map { item -> item.toSwipesUserCard() }
                        setEventState(ResponseState.Success)
                    }
                    is RequestResult.Error -> {
                        Timber.i("Error ${it.error}")
                        setEventState(ResponseState.Failure(it.error as ResponseError))
                    }
                }
            }, {
                Timber.i(" Throwable error $it")
                setEventState(ResponseState.Failure(ResponseError.ConnectionError))
            }).addTo(disposable)
    }
}
