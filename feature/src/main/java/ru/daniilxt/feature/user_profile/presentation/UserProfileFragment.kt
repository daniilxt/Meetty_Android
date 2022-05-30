package ru.daniilxt.feature.user_profile.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.addBackPressedCallback
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.setWindowTransparency
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentUserProfileBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.main_screen.presentation.INavigation

class UserProfileFragment : BaseFragment<UserProfileViewModel>(R.layout.fragment_user_profile) {

    override val binding: FragmentUserProfileBinding by viewBinding(FragmentUserProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as INavigation).showNavigation(false)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()

        requireActivity().setWindowTransparency { statusBarSize, navigationBarSize ->
        }
        addBackPressedCallback {
            viewModel.back()
        }
    }

    override fun setupViews() {
        super.setupViews()
        setupButtons()
    }

    private fun setupButtons() {
        binding.mbInfo.isChecked = true
        binding.mbInfo.setDebounceClickListener(delay = 0L) {
            binding.mbInfo.isChecked = true
            binding.mbAchievements.isChecked = false
        }
        binding.mbAchievements.setDebounceClickListener(delay = 0L) {
            binding.mbInfo.isChecked = false
            binding.mbAchievements.isChecked = true
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .userProfileComponentFactory()
            .create(this)
            .inject(this)
    }
}
