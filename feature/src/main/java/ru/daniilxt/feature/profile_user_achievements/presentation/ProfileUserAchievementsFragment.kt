package ru.daniilxt.feature.profile_user_achievements.presentation

import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.showDialog
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileUserAchievementsBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.dialogs.create_achievement.AchievementBottomSheet
import ru.daniilxt.feature.domain.model.UserAchievement
import ru.daniilxt.feature.profile_steps.presentation.adapter.IValidateFragmentFields
import ru.daniilxt.feature.profile_user_achievements.presentation.adapter.UserAchievementAdapter

class ProfileUserAchievementsFragment :
    BaseFragment<ProfileUserAchievementsViewModel>(R.layout.fragment_profile_user_achievements),
    IValidateFragmentFields {

    override val binding: FragmentProfileUserAchievementsBinding by viewBinding(
        FragmentProfileUserAchievementsBinding::bind
    )

    private val achieveAdapter by lazy {
        UserAchievementAdapter(onDeleteAchieveClickListener = {
            viewModel.deleteAchievement(it)
        })
    }

    private val achievementDialog by lazy {
        AchievementBottomSheet { bundle ->
            bundle.getParcelable<UserAchievement>(AchievementBottomSheet.RECEIVED_DATA_KEY)
                ?.let { it -> viewModel.addAchievement(it) }
        }
    }

    override fun setupViews() {
        super.setupViews()
        initRecyclerAdapter()
        setButtons()
    }

    private fun setButtons() {
        binding.tvAddAchieve.setDebounceClickListener {
            parentFragmentManager.showDialog(achievementDialog)
        }
    }

    private fun initRecyclerAdapter() {
        binding.includeRv.rvAchievements.adapter = achieveAdapter
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.userAchievements.observe {
            achieveAdapter.bind(it)
        }
    }

    override fun isFieldsFilled(callback: (isFilled: Boolean) -> Unit) {
        viewModel.putUserAchievements()
        callback(true)
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profileUserAchievementsComponentFactory()
            .create(this)
            .inject(this)
    }

    companion object {
        fun newInstance(): ProfileUserAchievementsFragment {
            return ProfileUserAchievementsFragment()
        }
    }
}
