package ru.daniilxt.feature.profile_user_achievements.presentation

import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileUserAchievementsBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
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

    override fun setupViews() {
        super.setupViews()
        initRecyclerAdapter()
    }

    private fun initRecyclerAdapter() {
        binding.rvAchievement.adapter = achieveAdapter
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.userAchievements.observe {
            achieveAdapter.bind(it)
        }
    }

    override fun isFieldsFilled(callback: (isFilled: Boolean) -> Unit) {
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
}
