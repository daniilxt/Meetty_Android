package ru.daniilxt.feature.user_profile.presentation

import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter

class UserProfileViewModel(private val router: FeatureRouter) : BaseViewModel() {
    fun back() {
        router.back()
    }
}
