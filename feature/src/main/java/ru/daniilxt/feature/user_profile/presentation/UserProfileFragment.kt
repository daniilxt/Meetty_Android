package ru.daniilxt.feature.user_profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.chip.Chip
import org.greenrobot.eventbus.EventBus
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.events.AuthEvent
import ru.daniilxt.common.extensions.addBackPressedCallback
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.setWindowTransparency
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.common.token.TokenRepository
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentUserProfileBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.domain.model.UserProfileInfo
import ru.daniilxt.feature.main_screen.presentation.INavigation
import ru.daniilxt.feature.profile_user_achievements.presentation.adapter.UserAchievementAdapter
import timber.log.Timber
import javax.inject.Inject

class UserProfileFragment : BaseFragment<UserProfileViewModel>(R.layout.fragment_user_profile) {

    override val binding: FragmentUserProfileBinding by viewBinding(FragmentUserProfileBinding::bind)

    @Inject
    lateinit var tokenRepository: TokenRepository

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

    override fun setupFromArguments(args: Bundle) {
        super.setupFromArguments(args)
        val isMy = args.getBoolean(DATA_KEY_IS_MY, false)
        val userId = args.getLong(DATA_KEY_USER_ID, -1)
        viewModel.loadUserProfile(isMy, userId)
    }

    override fun setupViews() {
        super.setupViews()
        setupButtons()
        initRecyclerAdapter()
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.userIfo.observe {
            if (it.isNotEmpty()) {
                initUserInfo(it.first())
            }
        }
    }

    private fun initUserInfo(data: UserProfileInfo) {
        binding.tvUsername.text = data.userInfo.getFullUserName()
        binding.ivUserAvatar.load(data.userInfo.avatarLink) {
            this.error(R.drawable.ic_in_progress_24)
            this.listener(onSuccess = { request, metadata ->
                binding.ivUserAvatarShimmer.stopLoading()
                binding.ivUserAvatarShimmer.isVisible = false
            })
        }

        if (data.userInfo.id == 49L) {
            binding.ibExit.setDebounceClickListener {
                EventBus.getDefault().post(AuthEvent())
            }
            binding.fabMessage.isVisible = false
        }
        binding.includeProfileInfo.tvGender.text = data.userInfo.sex
        binding.includeProfileInfo.tvBirthday.text = data.userAdditionalInfo.birthDay.toString()
        binding.includeProfileInfo.tvPhone.text = data.userAdditionalInfo.userPhone

        binding.includeProfileInfo.ivEduLogo.load(data.userEducationInfo.logoLink)
        binding.includeProfileInfo.tvEduName.text = data.userEducationInfo.name
        achieveAdapter.bind(data.userAchievements)
        for (item in data.userAdditionalInfo.categories) {
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

    companion object {
        private val TAG = UserProfileFragment::class.java.simpleName
        val DATA_KEY_IS_MY = "${TAG}_DATA_KEY_IS_MY"
        val DATA_KEY_USER_ID = "${TAG}_DATA_KEY_USER_ID"

        fun makeBundle(isMy: Boolean = false, userId: Long): Bundle {
            Timber.i("TO BUNDLE $isMy  $userId")
            return Bundle().apply {
                putBoolean(DATA_KEY_IS_MY, isMy)
                putLong(DATA_KEY_USER_ID, userId)
            }
        }
    }
}
