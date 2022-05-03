package ru.daniilxt.feature.profile_personal_info.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfilePersonalInfoBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.profile_steps.presentation.adapter.IValidateFragmentFields

class ProfilePersonalInfoFragment :
    BaseFragment<ProfilePersonalInfoViewModel>(R.layout.fragment_profile_personal_info),
    IValidateFragmentFields {

    override val binding: FragmentProfilePersonalInfoBinding by viewBinding(
        FragmentProfilePersonalInfoBinding::bind
    )
    private val etDelegate by lazy {
        InputFieldDelegate(binding)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
    }

    override fun setupViews() {
        super.setupViews()
        initTextFields()
        addNewDelegate(etDelegate)
    }

    private fun addNewDelegate(etDelegate: BaseDelegate) {
        etDelegate.loadDelegate()
    }

    override fun isFieldsFilled(callback: (isFilled: Boolean) -> Unit) {
        callback(true)
    }

    private fun initTextFields() {
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profilePersonalInfoComponentFactory()
            .create(this)
            .inject(this)
    }

    companion object {
        fun newInstance(): ProfilePersonalInfoFragment {
            return ProfilePersonalInfoFragment()
        }
    }
}
