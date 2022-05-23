package ru.daniilxt.feature.profile_steps.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.ErrorEntity
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseState
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import ru.daniilxt.feature.domain.usecase.SendRegistrationInfoUseCase

class ProfileStepsViewModel(
    private val router: FeatureRouter,
    private val dataWrapper: ProfileDataWrapper,
    private val sendRegistrationInfoUseCase: SendRegistrationInfoUseCase
) : BaseViewModel() {
    var currentSelectedPage = INITIAL_PAGE
        private set

    private var _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> get() = _uiState
    fun back() {
        router.back()
    }

    fun setCurrentSelectedPage(position: Int) {
        currentSelectedPage = position
    }

    fun signUp() {
        setEventState(ResponseState.Progress)
        _uiState.value = UiState.Processing
        sendRegistrationInfoUseCase.invoke(dataWrapper.getData())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        _uiState.value = UiState.Success
                        setEventState(ResponseState.Success)
                    }
                    is RequestResult.Error -> {
                        _uiState.value = UiState.Error(it.error)
                    }
                }
            }, {

            }).addTo(disposable)
    }

    sealed class UiState {
        object Initial : UiState()
        object Processing : UiState()
        object Success : UiState()
        data class Error(val errorEntity: ErrorEntity) : UiState()
    }

    companion object {
        const val INITIAL_PAGE = 0
    }
}
