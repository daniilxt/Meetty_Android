package ru.daniilxt.feature

import ru.daniilxt.feature.domain.model.UserDialog

interface FeatureRouter {
    fun back()
    fun openProfileFragment()
    fun openWelcomeDescriptionFragment()
    fun openOnboarding()
    fun openLoginFragment()
    fun openChat(userDialog: UserDialog)
}