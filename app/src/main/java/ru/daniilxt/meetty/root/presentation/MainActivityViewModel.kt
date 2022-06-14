package ru.daniilxt.meetty.root.presentation

import androidx.lifecycle.ViewModel
import ru.daniilxt.meetty.navigation.Navigator

class MainActivityViewModel(private val router: Navigator) : ViewModel() {
    fun openMessagesFragment() {
        router.openDialogs()
    }

    fun openHome() {
    }

    fun search() {
    }
}
