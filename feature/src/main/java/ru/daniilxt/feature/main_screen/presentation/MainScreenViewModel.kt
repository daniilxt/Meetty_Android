package ru.daniilxt.feature.main_screen.presentation

import androidx.lifecycle.ViewModel
import ru.daniilxt.feature.FeatureRouter

class MainScreenViewModel(
    private val router: FeatureRouter
) : ViewModel() {
    fun openProfileFragment() {
        router.openProfileFragment()
    }
}