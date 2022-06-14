package ru.daniilxt.feature.user_dialogs.presentation

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
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.domain.usecase.GetUserDialogsUseCase

@SuppressLint("NewApi")
class UserDialogsViewModel(
    private val router: FeatureRouter,
    private val getUserDialogsUseCase: GetUserDialogsUseCase
) : BaseViewModel() {
    private var _dialogs: MutableStateFlow<List<UserDialog>> = MutableStateFlow(emptyList())
    val dialogs: StateFlow<List<UserDialog>> get() = _dialogs

    init {
        loadDialogs()
    }

    private fun loadDialogs() {
        setEventState(ResponseState.Progress)
        getUserDialogsUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        setEventState(ResponseState.Success)
                        _dialogs.value = it.data.sortedByDescending { item -> item.lastMessage.dateTime }
                    }
                    is RequestResult.Error -> {
                        setEventState(ResponseState.Failure(it.error as ResponseError))
                    }
                }
            }, {
                setEventState(ResponseState.Failure(ResponseError.ConnectionError))
            }).addTo(disposable)
    }

    fun openChat(userDialog: UserDialog) {
        router.openChat(userDialog)
    }
}
