package ru.daniilxt.feature.profile_registration.presentation

import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileRegistrationBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.feature.profile_steps.presentation.adapter.IValidateFragmentFields

class ProfileRegistrationFragment :
    BaseFragment<ProfileRegistrationViewModel>(R.layout.fragment_profile_registration),
    IValidateFragmentFields {

    override val binding: FragmentProfileRegistrationBinding by viewBinding(
        FragmentProfileRegistrationBinding::bind
    )
    private val etDelegate by lazy {
        InputFieldDelegate(binding, viewModel)
    }

    override fun setupViews() {
        super.setupViews()
        addNewDelegate(etDelegate)
    }

    override fun isFieldsFilled(callback: (isFilled: Boolean) -> Unit) {
        val isFilled = etDelegate.isFieldsCorrectAndPutToBundle()
        callback(isFilled)
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profileRegistrationComponentFactory()
            .create(this)
            .inject(this)
    }

    private fun addNewDelegate(etDelegate: BaseDelegate) {
        etDelegate.loadDelegate()
    }

    companion object {
        fun newInstance(): ProfileRegistrationFragment {
            return ProfileRegistrationFragment()
        }
    }
}
