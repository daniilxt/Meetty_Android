package ru.daniilxt.feature.navigation

import androidx.navigation.NavController
import ru.daniilxt.feature.R
import ru.daniilxt.feature.navigation.interfaces.MainScreenRouter

class MainScreenNavigator : MainScreenRouter {

    private var navController: NavController? = null

    fun attach(navController: NavController) {
        this.navController = navController
    }

    fun detach() {
        navController = null
    }

    override fun openMainScreenMapFragment() {
        navController?.navigate(
            R.id.open_main_screen_map_fragment
        )
    }

    override fun openMainScreenUserCardFragment() {
        navController?.navigate(
            R.id.open_main_screen_user_card_fragment
        )
    }

    override fun openMainScreenUserListFragment() {
        navController?.navigate(
            R.id.open_main_screen_user_list_fragment
        )
    }
}
