package ru.daniilxt.feature.profile_user_education.presentation

import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileUserEducationBinding

class InputFieldDelegate(
    private val binding: FragmentProfileUserEducationBinding,
    private val viewModel: ProfileUserEducationViewModel
) :
    BaseDelegate(binding) {
    override fun loadDelegate() {
        super.loadDelegate()
        initViews()
    }

    private fun initViews() {
        binding.spinnerCities.spinnerWrapper.hint = context.getString(R.string.select_city)
        binding.spinnerInstitutes.spinnerWrapper.hint = context.getString(R.string.select_institute)
    }

    fun isFieldsCorrectAndPutToBundle(): Boolean {
/*        val email = binding.etEmail.textInputEt
        if (!viewModel.isEmailValid(email.text.toString())) {
            email.error = context.getString(R.string.email_incorrect)
            return false
        }
        val password = binding.etPassword.textInputEt
        val confirmPassword = binding.etConfirmPassword.textInputEt
        if (!viewModel.isPasswordsEqual(password.text.toString(), confirmPassword.text.toString())
        ) {
            confirmPassword.error = context.getString(R.string.passwords_not_match)
            return false
        }
        viewModel.putRegistrationData(email.text.toString(), password.text.toString())
        return true*/
        return true
    }
}
