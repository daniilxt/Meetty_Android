package ru.daniilxt.feature.profile_user_education.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileUserEducationBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.profile_steps.presentation.adapter.IValidateFragmentFields

class ProfileUserEducationFragment :
    BaseFragment<ProfileUserEducationViewModel>(R.layout.fragment_profile_user_education),
    IValidateFragmentFields {

    override val binding: FragmentProfileUserEducationBinding by viewBinding(
        FragmentProfileUserEducationBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
    }

    override fun isFieldsFilled(callback: (isFilled: Boolean) -> Unit) {
        callback(true)
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profileUserEducationComponentFactory()
            .create(this)
            .inject(this)
    }

    companion object {
        fun newInstance(): ProfileUserEducationFragment {
            return ProfileUserEducationFragment()
        }
    }
}
