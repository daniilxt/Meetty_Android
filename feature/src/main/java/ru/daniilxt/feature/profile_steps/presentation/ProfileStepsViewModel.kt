package ru.daniilxt.feature.profile_steps.presentation

import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter

class ProfileStepsViewModel(private val router: FeatureRouter) : BaseViewModel() {
    var currentSelectedPage = INITIAL_PAGE
        private set

    fun back() {
        router.back()
    }

    fun setCurrentSelectedPage(position: Int) {
        currentSelectedPage = position
    }

    companion object {
        const val INITIAL_PAGE = 0
    }
}
