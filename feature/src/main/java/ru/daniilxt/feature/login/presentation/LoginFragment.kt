package ru.daniilxt.feature.login.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.setWindowTransparency
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentLoginBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent


class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    override val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    private val etDelegate by lazy {
        InputFieldDelegate(binding, viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
        requireActivity().setWindowTransparency { statusBarSize, navigationBarSize ->
            // Проставление отступов
        }
    }

    override fun setupViews() {
        super.setupViews()
        addNewDelegate(etDelegate)
        initButtons()
    }

    private fun addNewDelegate(etDelegate: BaseDelegate) {
        etDelegate.loadDelegate()
    }

    private fun initButtons() {
        binding.signIn.setDebounceClickListener {
            etDelegate.checkFieldsAndSignIn()
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .loginComponentFactory()
            .create(this)
            .inject(this)
    }
}
