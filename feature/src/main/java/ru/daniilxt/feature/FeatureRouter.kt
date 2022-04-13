package ru.daniilxt.feature

interface FeatureRouter {
    fun back()
    fun openProfileFragment()
    fun openWelcomeDescriptionFragment()
    fun openOnboarding()
    fun openLoginFragment()
    fun openChat(chatId: Long)
}