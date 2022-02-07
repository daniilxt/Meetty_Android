package io.daniilxt.fintech.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import io.daniilxt.feature.FeatureRouter

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
}