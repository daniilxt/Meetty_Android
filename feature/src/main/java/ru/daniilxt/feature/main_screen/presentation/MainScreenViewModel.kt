package ru.daniilxt.feature.main_screen.presentation

import android.annotation.SuppressLint
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.navigation.interfaces.MainScreenRouter

@SuppressLint("NewApi")
class MainScreenViewModel(
    private val router: FeatureRouter,
    private val mainScreenRouter: MainScreenRouter,
) : BaseViewModel() {
    fun openMapFragment() {
        mainScreenRouter.openMainScreenMapFragment()
    }

    fun openMainScreenUserCardFragment() {
        mainScreenRouter.openMainScreenUserCardFragment()
    }

    fun openUserListFragment() {
        mainScreenRouter.openMainScreenUserListFragment()
    }

    fun openUserProfile(isMy: Boolean, userId: Long) {
        router.openUserProfile(isMy, userId)
    }
}
