package ru.daniilxt.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.model.ResponseState

open class BaseViewModel : ViewModel() {

    private val _eventState: MutableStateFlow<ResponseState> = MutableStateFlow(ResponseState.Initial)
    val eventState: StateFlow<ResponseState> = _eventState

    protected val disposable = CompositeDisposable()

    fun setEventState(state: ResponseState) {
        _eventState.value = state
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}
