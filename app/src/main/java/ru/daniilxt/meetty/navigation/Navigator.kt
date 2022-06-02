package ru.daniilxt.meetty.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.chat.presentation.UserChatFragment
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.user_profile.presentation.UserProfileFragment
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

    override fun openMainScreenFragment() {
        when (navController?.currentDestination?.id) {
            R.id.profileStepsFragment -> {
                navController?.navigate(R.id.action_profileStepsFragment_to_mainScreenFragment)
            }
            R.id.loginFragment -> {
                navController?.navigate(R.id.action_loginFragment_to_mainScreenFragment)
            }
        }
    }

    override fun openProfileStepsFragment() {
        when (navController?.currentDestination?.id) {
            R.id.onboardingFragment -> {
                navController?.navigate(R.id.action_onboardingFragment_to_profileStepsFragment)
            }
        }
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

    override fun openUserProfile(isMy: Boolean, userId: Long) {
        when (navController?.currentDestination?.id) {
            R.id.mainScreenFragment -> {
                val bundle = UserProfileFragment.makeBundle(isMy, userId)
                navController?.navigate(
                    R.id.action_mainScreenFragment_to_userProfileFragment, bundle
                )
            }
        }
    }

    override fun openChat(userDialog: UserDialog) {
        when (navController?.currentDestination?.id) {
            R.id.userDialogsFragment -> {
                val bundle = UserChatFragment.makeBundle(userDialog)
                navController?.navigate(
                    R.id.action_userDialogsFragment_to_userChatFragment, bundle
                )
            }
        }
    }
}
