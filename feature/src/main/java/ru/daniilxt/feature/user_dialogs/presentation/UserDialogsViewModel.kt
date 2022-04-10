package ru.daniilxt.feature.user_dialogs.presentation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider

@SuppressLint("NewApi")
class UserDialogsViewModel(private val router: FeatureRouter) : BaseViewModel() {

    private var _dialogs: MutableStateFlow<List<UserDialog>> = MutableStateFlow(emptyList())
    val dialogs: StateFlow<List<UserDialog>> get() = _dialogs

    init {
        _dialogs.value = UserDialogsProvider.getUserDialogs()
    }
}
