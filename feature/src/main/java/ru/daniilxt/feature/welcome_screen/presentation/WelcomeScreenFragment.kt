package ru.daniilxt.feature.welcome_screen.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.clearLightStatusBar
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.setWindowTransparency
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentWelcomeScreenBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent

class WelcomeScreenFragment :
    BaseFragment<WelcomeScreenViewModel>(R.layout.fragment_welcome_screen) {

    override val binding: FragmentWelcomeScreenBinding by viewBinding(FragmentWelcomeScreenBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().clearLightStatusBar()
        hideSystemBars()
        initButtons()
    }

    private fun hideSystemBars() {
        requireActivity().setWindowTransparency { statusBarSize, navigationBarSize ->
            // Проставление отступов
        }
    }

    private fun initButtons() {
        binding.mbNext.setDebounceClickListener {
            viewModel.openWelcomeAbout()
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .welcomeScreenComponentFactory()
            .create(this)
            .inject(this)
    }
}
