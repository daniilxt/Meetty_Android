package ru.daniilxt.feature.profile_steps.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.hideKeyboardWithDelay
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileStepsBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.profile_personal_info.presentation.ProfilePersonalInfoFragment
import ru.daniilxt.feature.profile_steps.presentation.adapter.ViewPagerAdapter
import ru.daniilxt.feature.profile_user_achievements.presentation.ProfileUserAchievementsFragment
import ru.daniilxt.feature.profile_user_education.presentation.ProfileUserEducationFragment

class ProfileStepsFragment : BaseFragment<ProfileStepsViewModel>(R.layout.fragment_profile_steps) {

    override val binding: FragmentProfileStepsBinding by viewBinding(FragmentProfileStepsBinding::bind)

    private val currentFragment: Fragment?
        get() {
            return childFragmentManager.findFragmentByTag(
                FRAGMENT_TAG + viewPagerAdapter.getItemId(viewModel.currentSelectedPage)
            )
        }
    var lastUsedFrg: Fragment? = null

    val fragmentsTitleList: Array<String> by lazy {
        requireContext().resources.getStringArray(R.array.profile_steps)
    }

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(
            this,
            listOf(
                ProfilePersonalInfoFragment.newInstance(),
                ProfileUserAchievementsFragment.newInstance(),
                ProfileUserAchievementsFragment.newInstance(),
                ProfileUserEducationFragment.newInstance()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
        initViewPager()
        initButtons()
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO why crash
//        binding.viewPager.unregisterOnPageChangeCallback(viewPagerCallback)
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
