package ru.daniilxt.feature.main_screen_user_list.presentation

import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentMainScreenUserListBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.main_screen_user_list.presentation.adapter.UserCardAdapter

class MainScreenUserListFragment :
    BaseFragment<MainScreenUserListViewModel>(R.layout.fragment_main_screen_user_list) {

    override val binding: FragmentMainScreenUserListBinding by viewBinding(
        FragmentMainScreenUserListBinding::bind
    )
    private val userCardsAdapter by lazy {
        UserCardAdapter {
        }
    }

    override fun setupViews() {
        super.setupViews()
        initRecyclerView()
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.userCards.observe {
            userCardsAdapter.bind(it)
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenUserListComponentFactory()
            .create(this)
            .inject(this)
    }

    private fun initRecyclerView() {
        binding.rvUserCards.adapter = userCardsAdapter
    }
}
