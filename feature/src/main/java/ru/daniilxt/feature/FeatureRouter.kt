package ru.daniilxt.feature

import ru.daniilxt.feature.domain.model.UserDialog

interface FeatureRouter {
    fun back()
    fun openWelcomeDescriptionFragment()
    fun openOnboarding()
    fun openLoginFragment()
    fun openProfileStepsFragment()
    fun openMainScreenFragment()
    fun openUserProfile(isMy: Boolean = false, userId: Long)
    fun openChat(userDialog: UserDialog)
    fun openDialogs()
}
