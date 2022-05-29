package ru.daniilxt.meetty.root.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.main_screen.presentation.INavigation
import ru.daniilxt.meetty.R
import ru.daniilxt.meetty.databinding.ActivityMainBinding
import ru.daniilxt.meetty.navigation.Navigator
import ru.daniilxt.meetty.root.di.RootApi
import ru.daniilxt.meetty.root.di.RootComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity(), INavigation {

    @Inject
    lateinit var activityViewModel: MainActivityViewModel

    @Inject
    lateinit var navigator: Navigator

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inject()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = navHostFragment.navController
        navigator.attach(navController, this)

        setupNavigationButtons()
    }

    private fun setupNavigationButtons() {
        setBottomNavVisibility()
        binding.ibMessages.setDebounceClickListener {
            activityViewModel.openMessagesFragment()
        }
        binding.ibHome.setDebounceClickListener {
            activityViewModel.openHome()
        }
        binding.ibSearch.setDebounceClickListener {
            activityViewModel.search()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.detach()
    }

    override fun showNavigation(isVisible: Boolean) {
        setBottomNavVisibility(isVisible)
    }

    private fun setBottomNavVisibility(isVisible: Boolean = false) {
        binding.ibSearch.isVisible = isVisible
        binding.bottomNavWrapper.isVisible = isVisible
    }

    private fun inject() {
        FeatureUtils.getFeature<RootComponent>(this, RootApi::class.java)
            .mainActivityComponentFactory()
            .create(this)
            .inject(this)
    }
}
