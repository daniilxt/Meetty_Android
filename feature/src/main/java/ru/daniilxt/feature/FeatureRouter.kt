package ru.daniilxt.feature

interface FeatureRouter {
    fun back()
    fun openWelcomeDescriptionFragment()
    fun openOnboarding()
    fun openLoginFragment()
    fun openProfileStepsFragment()
    fun openMainScreenFragment()
    fun openUserProfile(isMy: Boolean = false, userId: Long)
}
