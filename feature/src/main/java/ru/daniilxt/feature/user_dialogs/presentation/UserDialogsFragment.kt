package ru.daniilxt.feature.user_dialogs.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.dpToPx
import ru.daniilxt.common.extensions.margin
import ru.daniilxt.common.extensions.removeAppBarElevation
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.setWindowTransparency
import ru.daniilxt.common.token.TokenRepository
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentUserDialogsBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.main_screen.presentation.INavigation
import ru.daniilxt.feature.user_dialogs.presentation.adapter.UserDialogsAdapter
import javax.inject.Inject

class UserDialogsFragment : BaseFragment<UserDialogsViewModel>(R.layout.fragment_user_dialogs) {

    private var _binding: FragmentUserDialogsBinding? = null
    override val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var tokenRepository: TokenRepository

    private val userDialogsAdapter by lazy {
        UserDialogsAdapter(tokenRepository.getCurrentUserId()) { userDialog ->
            viewModel.openChat(userDialog)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDialogsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as INavigation).showNavigation(false)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
        requireActivity().setWindowTransparency { _, _ ->
            binding.layoutToolbar.root.margin(top = requireContext().dpToPx(18F))
        }
    }

    override fun setupViews() {
        super.setupViews()
        initToolbar()
        initRecycler()
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.dialogs.observe {
            userDialogsAdapter.bind(it)
        }
    }

    private fun initRecycler() {
        binding.dialogsRv.adapter = userDialogsAdapter
    }

    private fun initToolbar() {
        binding.layoutToolbar.includeToolbarBackTvTitle.text = getString(R.string.dialogs)
        binding.layoutToolbar.root.removeAppBarElevation()
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .userDialogsComponentFactory()
            .create(this)
            .inject(this)
    }
}
