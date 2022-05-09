package ru.daniilxt.feature.login.presentation

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentLoginBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent

class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    override val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
/*            requireActivity().window.clearFlags(WindowManager
                .LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        */
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .loginComponentFactory()
            .create(this)
            .inject(this)
    }
}
