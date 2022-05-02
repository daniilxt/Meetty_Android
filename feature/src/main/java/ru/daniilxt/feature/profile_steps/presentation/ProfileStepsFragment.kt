package ru.daniilxt.feature.profile_steps.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileStepsBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent

class ProfileStepsFragment : BaseFragment<ProfileStepsViewModel>(R.layout.fragment_profile_steps) {

    override val binding: FragmentProfileStepsBinding by viewBinding(FragmentProfileStepsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profileStepsComponentFactory()
            .create(this)
            .inject(this)
    }
}
