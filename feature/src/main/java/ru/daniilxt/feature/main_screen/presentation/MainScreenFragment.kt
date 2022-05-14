package ru.daniilxt.feature.main_screen.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import coil.load
import coil.transform.CircleCropTransformation
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.clearLightStatusBar
import ru.daniilxt.common.extensions.dpToPx
import ru.daniilxt.common.extensions.margin
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setNavigationBarColor
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentMainScreenBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.main_screen.presentation.adapter.UserCardAdapter

class MainScreenFragment : BaseFragment<MainScreenViewModel>(R.layout.fragment_main_screen) {
    override val binding: FragmentMainScreenBinding by viewBinding(FragmentMainScreenBinding::bind)

    private val userCardsAdapter by lazy {
        UserCardAdapter {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.background_primary_dark)
        requireView().clearLightStatusBar()
        requireActivity().setNavigationBarColor(R.color.white)
        binding.contentLayout.margin(top = requireContext().dpToPx(20F))
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_screen_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.mbSwitch.setDebounceClickListener {
            navController.navigate(R.id.open_main_screen_user_card_fragment)
        }
    }

    override fun setupViews() {
        super.setupViews()
        initToolbar()
        //  initBottomSheet()
        initRecyclerView()
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.userCards.observe {
            userCardsAdapter.bind(it)
        }
    }

    private fun initRecyclerView() {
        //    binding.rvUserCards.adapter = userCardsAdapter
    }

    private fun initToolbar() {
        binding.ivProfile.load("https://sun9-30.userapi.com/sun9-57/s/v1/if1/5uukGklSa12cTtzRaFAB6rnxSEy0078CECF4Bt80CEJibU979WejyH1haetd5cLfqPPvliyc.jpg?size=899x1280&quality=96&type=album") {
            transformations(CircleCropTransformation())
        }
        val backdropAnimation = BackdropViewAnimation(
            requireContext(), binding.backDrop, binding.contentLayout,
            R.drawable.ic_menu_2_24, R.drawable.ic_close_24, R.color.white
        )
        binding.ivMenu.setDebounceClickListener { v -> backdropAnimation.toggle(v) }
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenComponentFactory()
            .create(this)
            .inject(this)
    }
}
