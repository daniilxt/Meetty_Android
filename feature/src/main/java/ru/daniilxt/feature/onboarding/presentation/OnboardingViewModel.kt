package ru.daniilxt.feature.onboarding.presentation

import androidx.lifecycle.ViewModel
import ru.daniilxt.feature.FeatureRouter

class OnboardingViewModel(
    private val router: FeatureRouter
) : ViewModel() {
    fun openProfileFragment() {
        router.openProfileFragment()
    }
}