package ru.daniilxt.feature.welcome_screen_description.presentation

import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter

class WelcomeScreenDescriptionViewModel(private val router: FeatureRouter) : BaseViewModel() {
    fun openOnboarding() {
        router.openOnboarding()
    }
}
