package ru.daniilxt.meetty.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.meetty.R

class Navigator : FeatureRouter {

    private var navController: NavController? = null
    private var activity: AppCompatActivity? = null

    fun attach(navController: NavController, activity: AppCompatActivity) {
        this.navController = navController
        this.activity = activity
    }

    fun detach() {
        navController = null
        activity = null
    }

    private companion object {
        private val TAG = Navigator::class.simpleName
    }

    override fun openProfileFragment() {
    }

    override fun back() {
        val popped = navController!!.popBackStack()
        if (!popped) {
            activity!!.finish()
        }
    }

    override fun openWelcomeDescriptionFragment() {
        when (navController?.currentDestination?.id) {
            R.id.welcomeScreenFragment -> {
                navController?.navigate(
                    R.id.action_welcomeScreenFragment_to_welcomeScreenDescriptionFragment
                )
            }
        }
    }

    override fun openOnboarding() {
        when (navController?.currentDestination?.id) {
            R.id.welcomeScreenDescriptionFragment -> {
                navController?.navigate(
                    R.id.action_welcomeScreenDescriptionFragment_to_onboardingFragment
                )
            }
        }
    }

    override fun openLoginFragment() {
        when (navController?.currentDestination?.id) {
            R.id.onboardingFragment -> {
                navController?.navigate(
                    R.id.action_onboardingFragment_to_loginFragment
                )
            }
        }
    }
}