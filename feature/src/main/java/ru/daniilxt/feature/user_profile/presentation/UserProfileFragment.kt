package ru.daniilxt.feature.user_profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
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
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import ru.daniilxt.feature.main_screen.presentation.INavigation
import ru.daniilxt.feature.profile_user_achievements.presentation.adapter.UserAchievementAdapter

class UserProfileFragment : BaseFragment<UserProfileViewModel>(R.layout.fragment_user_profile) {

    override val binding: FragmentUserProfileBinding by viewBinding(FragmentUserProfileBinding::bind)

    private val achieveAdapter by lazy {
        UserAchievementAdapter(onDeleteAchieveClickListener = {
        }, isDeletable = false)
    }

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
        initRecyclerAdapter()
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.userAchievements.observe {
            initUserInfo()
            achieveAdapter.bind(it)
        }
    }

    private fun initUserInfo() {
        val data = List(10) {
            ProfessionalInterest(it.toLong(), "Программирование $it")
        }
        for (item in data) {
            val layoutInflater = LayoutInflater.from(binding.root.context)
            val chip = (layoutInflater.inflate(R.layout.item_chip, null) as Chip).apply {
                id = View.generateViewId()
                isChecked = true
                isClickable = false
                text = item.interestName
            }
            binding.includeProfileInfo.chipGroupInterests.addView(chip)
        }
    }

    private fun setupButtons() {
        binding.ibBack.setDebounceClickListener {
            viewModel.back()
        }
        setSwitcherChildViewsVisibility(true)
        binding.profileSwitcher.mbInfo.setDebounceClickListener(delay = 0L) {
            setSwitcherChildViewsVisibility(true)
        }
        binding.profileSwitcher.mbAchievements.setDebounceClickListener(delay = 0L) {
            setSwitcherChildViewsVisibility(false)
        }
    }

    private fun setSwitcherChildViewsVisibility(isVisible: Boolean) {
        // set switcher position
        binding.profileSwitcher.mbInfo.isChecked = isVisible
        binding.profileSwitcher.mbAchievements.isChecked = !isVisible

        // change layout visibility by switcher
        binding.includeProfileInfo.root.isVisible = isVisible
        binding.includeRvAchievements.root.isVisible = !isVisible
    }

    private fun initRecyclerAdapter() {
        binding.includeRvAchievements.rvAchievements.adapter = achieveAdapter
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .userProfileComponentFactory()
            .create(this)
            .inject(this)
    }
}
