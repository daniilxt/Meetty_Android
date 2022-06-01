package ru.daniilxt.feature.main_screen.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import coil.load
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.clearLightStatusBar
import ru.daniilxt.common.extensions.dpToPx
import ru.daniilxt.common.extensions.margin
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setNavigationBarColor
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.setWindowTransparency
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentMainScreenBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.domain.model.UserProfileInfo
import ru.daniilxt.feature.navigation.MainScreenNavigator
import javax.inject.Inject

class MainScreenFragment : BaseFragment<MainScreenViewModel>(R.layout.fragment_main_screen) {
    override val binding: FragmentMainScreenBinding by viewBinding(FragmentMainScreenBinding::bind)

    @Inject
    lateinit var navigator: MainScreenNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as INavigation).showNavigation(true)

        requireActivity().setStatusBarColor(R.color.background_primary_dark)
        requireView().clearLightStatusBar()
        requireActivity().setNavigationBarColor(R.color.white)

        requireActivity().setWindowTransparency { _, _ ->
            binding.contentLayout.margin(top = requireContext().dpToPx(40F))
            binding.toolbar.root.margin(top = requireContext().dpToPx(20F))
        }
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_screen_container) as NavHostFragment
        val navController = navHostFragment.navController

        navigator.attach(navController)
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.userIfo.observe {
            if (it.isNotEmpty()) {
                initUserInfo(it.first())
            }
        }
    }

    private fun initUserInfo(userProfileInfo: UserProfileInfo) {
        binding.toolbar.ibProfile.load(userProfileInfo.userInfo.avatarLink) {
            transformations(CircleCropTransformation())
        }
        binding.toolbar.tvGreeting.text =
            getString(R.string.toolbar_user_profile_name, userProfileInfo.userInfo.firstName)
    }

    override fun setupViews() {
        super.setupViews()
        initToolbar()
        initButtons()
    }

    private fun initButtons() {
        with(binding.switcher) {
            mbCards.isChecked = true
            mbCards.setDebounceClickListener(delay = 0L) {
                setButtonCheckStatus(cards = true)
                viewModel.openUserListFragment()
            }
            mbLocation.setDebounceClickListener(delay = 0L) {
                setButtonCheckStatus(location = true)
                viewModel.openMapFragment()
            }
            mbRandom.setDebounceClickListener(delay = 0L) {
                setButtonCheckStatus(random = true)
                viewModel.openMainScreenUserCardFragment()
            }
        }
        binding.toolbar.ibProfile.setDebounceClickListener {
            viewModel.openUserProfile(true, -1)
        }
    }

    private fun setButtonCheckStatus(
        cards: Boolean = false,
        location: Boolean = false,
        random: Boolean = false
    ) {
        with(binding.switcher) {
            mbCards.isChecked = cards
            mbLocation.isChecked = location
            mbRandom.isChecked = random
        }
        lifecycleScope.launch {
            delay(300)
            binding.toolbar.ibMenu.performClick()
        }
    }

    private fun initToolbar() {
        val backdropAnimation = BackdropViewAnimation(
            requireContext(), binding.backDrop, binding.contentLayout,
            R.drawable.ic_menu_2_24, R.drawable.ic_close_24, R.color.white
        )
        binding.toolbar.ibMenu.setDebounceClickListener { v -> backdropAnimation.toggle(v) }
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenComponentFactory()
            .create(this)
            .inject(this)
    }
}
