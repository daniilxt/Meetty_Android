package ru.daniilxt.feature.onboarding.presentation

import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter

class OnboardingViewModel(private val router: FeatureRouter) : BaseViewModel() {
    fun openLoginFragment() {
        router.openLoginFragment()
    }

    fun openProfileStepsFragment() {
        router.openProfileStepsFragment()
    }
}