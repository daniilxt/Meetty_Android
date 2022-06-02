package ru.daniilxt.feature.login.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.setWindowTransparency
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.common.model.ResponseState
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
        requireActivity().setWindowTransparency { _, _ -> }
    }

    override fun setupViews() {
        super.setupViews()
        addNewDelegate(etDelegate)
        initButtons()
    }

    override fun handleEventState(eventState: ResponseState) {
        when (eventState) {
            is ResponseState.Success -> viewModel.openMainScreenFragment()
            is ResponseState.Progress -> {
            }
            is ResponseState.Initial -> {
            }
            is ResponseState.Failure -> {
            }
            is ResponseState.Error -> {
                Toast.makeText(requireContext(), eventState.error.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }
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
