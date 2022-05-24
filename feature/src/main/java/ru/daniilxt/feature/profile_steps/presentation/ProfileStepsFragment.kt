package ru.daniilxt.feature.profile_steps.presentation

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.error.RegistrationError
import ru.daniilxt.common.extensions.hideKeyboardWithDelay
import ru.daniilxt.common.extensions.margin
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setNavigationBarColor
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.setWindowTransparency
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileStepsBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.profile_personal_info.presentation.ProfilePersonalInfoFragment
import ru.daniilxt.feature.profile_professional_interests.presentation.ProfileProfessionalInterestsFragment
import ru.daniilxt.feature.profile_registration.presentation.ProfileRegistrationFragment
import ru.daniilxt.feature.profile_steps.presentation.adapter.ViewPagerAdapter
import ru.daniilxt.feature.profile_user_achievements.presentation.ProfileUserAchievementsFragment
import ru.daniilxt.feature.profile_user_education.presentation.ProfileUserEducationFragment

class ProfileStepsFragment : BaseFragment<ProfileStepsViewModel>(R.layout.fragment_profile_steps) {

    private var _binding: FragmentProfileStepsBinding? = null
    override val binding get() = requireNotNull(_binding)

    private val currentFragment: Fragment?
        get() {
            return childFragmentManager.findFragmentByTag(
                FRAGMENT_TAG + viewPagerAdapter.getItemId(viewModel.currentSelectedPage)
            )
        }
    private var lastUsedFrg: Fragment? = null

    private val fragmentsTitleList: Array<String> by lazy {
        requireContext().resources.getStringArray(R.array.profile_steps)
    }

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(
            this,
            listOf(
                ProfileRegistrationFragment.newInstance(),
                ProfilePersonalInfoFragment.newInstance(),
                ProfileUserEducationFragment.newInstance(),
                ProfileProfessionalInterestsFragment.newInstance(),
                ProfileUserAchievementsFragment.newInstance(),
            )
        )
    }
    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            viewModel.setCurrentSelectedPage(position)
            lastUsedFrg = currentFragment
            binding.tvStepTitle.text = fragmentsTitleList[position]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileStepsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setStatusBarColor(R.color.background_third)
        requireView().setLightStatusBar()
        requireActivity().setNavigationBarColor(R.color.white)
        requireActivity().setWindowTransparency { statusBarSize, _ ->
            binding.tvStepTitle.margin(top = (statusBarSize / 1.5).toFloat())
        }
        val rootView = binding.root as ViewGroup
        val layoutTransition = rootView.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        initViewPager()
        initButtons()
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.uiState.observe { handleUiState(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO why crash
        binding.viewPager.unregisterOnPageChangeCallback(viewPagerCallback)
        _binding = null
    }

    private fun initViewPager() {
        binding.viewPager.adapter = viewPagerAdapter
        binding.includeBottomStepMenu.pageIndicator.setViewPager(binding.viewPager)
        binding.viewPager.setCurrentItem(
            viewModel.currentSelectedPage,
            true
        )

        binding.viewPager.registerOnPageChangeCallback(viewPagerCallback)

        // This is needed to disable user scrolling
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = viewPagerAdapter.itemCount
    }

    private fun initButtons() {
        binding.includeBottomStepMenu.mbNext.setDebounceClickListener {
            currentFragment?.let { frg ->
                viewPagerAdapter.checkFragmentAlacrity(frg) { isFieldsFilled ->
                    if (isFieldsFilled) {
                        openNextPage()
                    }
                }
            }
        }
        binding.includeBottomStepMenu.mbPrev.setDebounceClickListener {
            openPreviousPage()
        }
        binding.includeBottomStepMenu.mbSkip.setDebounceClickListener {
        }
    }

    private fun openNextPage() {
        if (binding.viewPager.currentItem == viewPagerAdapter.itemCount - 1) {
            handleProfileEndFilling()
            return
        }
        hideKeyboardWithDelay {
            binding.viewPager.setCurrentItem(
                viewModel.currentSelectedPage + 1,
                true
            )
        }
    }

    private fun openPreviousPage() {
        hideKeyboardWithDelay {
            if (viewModel.currentSelectedPage == 0) {
                viewModel.back()
                return@hideKeyboardWithDelay
            }
            binding.viewPager.setCurrentItem(
                viewModel.currentSelectedPage - 1,
                true
            )
        }
    }

    private fun handleProfileEndFilling() {
        //viewModel.openMainScreenFragment()
        viewModel.signUp()
    }

    private fun handleUiState(uiState: ProfileStepsViewModel.UiState) {
        when (uiState) {
            is ProfileStepsViewModel.UiState.Initial -> {
                binding.pbProgress.visibility = View.GONE
            }
            is ProfileStepsViewModel.UiState.Processing -> {
                binding.pbProgress.visibility = View.VISIBLE
            }
            is ProfileStepsViewModel.UiState.Success -> {
                binding.pbProgress.visibility = View.GONE
                viewModel.openMainScreenFragment()
            }
            is ProfileStepsViewModel.UiState.Error -> {
                binding.pbProgress.visibility = View.GONE
                when (uiState.errorEntity) {
                    RegistrationError.UserAlreadyExists -> {
                        Toast.makeText(requireContext(), "User already exist", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profileStepsComponentFactory()
            .create(this)
            .inject(this)
    }

    companion object {
        private const val FRAGMENT_TAG = "f"
    }
}
