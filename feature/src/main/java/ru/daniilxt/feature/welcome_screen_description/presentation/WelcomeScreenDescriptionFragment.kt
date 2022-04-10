package ru.daniilxt.feature.welcome_screen_description.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.clearLightStatusBar
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentWelcomeScreenDescriptionBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.welcome_screen_description.presentation.adapter.AppFeatureDescriptionAdapter
import ru.daniilxt.feature.welcome_screen_description.presentation.util.AppFeatureDescriptionProvider

class WelcomeScreenDescriptionFragment :
    BaseFragment<WelcomeScreenDescriptionViewModel>(R.layout.fragment_welcome_screen_description) {

    override val binding: FragmentWelcomeScreenDescriptionBinding by viewBinding(
        FragmentWelcomeScreenDescriptionBinding::bind
    )
    private val featureDescriptionAdapter = AppFeatureDescriptionAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().clearLightStatusBar()
        initButtons()
    }

    private fun initButtons() {
        binding.mbNext.setDebounceClickListener {
            viewModel.openOnboarding()
        }
    }

    override fun setupViews() {
        super.setupViews()
        binding.rvFeatureDescription.adapter = featureDescriptionAdapter
        featureDescriptionAdapter.bind(
            AppFeatureDescriptionProvider.getFeatureDescription(
                requireContext()
            )
        )
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .welcomeScreenDescriptionComponentFactory()
            .create(this)
            .inject(this)
    }
}
