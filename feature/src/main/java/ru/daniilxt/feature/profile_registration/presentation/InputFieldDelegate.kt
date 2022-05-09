package ru.daniilxt.feature.profile_registration.presentation

import android.text.InputType
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.extensions.setInputFormAttributes
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileRegistrationBinding

class InputFieldDelegate(
    private val binding: FragmentProfileRegistrationBinding,
    private val viewModel: ProfileRegistrationViewModel
) :
    BaseDelegate(binding) {
    override fun loadDelegate() {
        super.loadDelegate()
        initViews()
    }

    private fun initViews() {
        binding.etEmail.setInputFormAttributes(
            hintText = context.getString(R.string.email),
            inputType = (
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                )
        )
        binding.etPassword.setInputFormAttributes(
            hintText = context.getString(R.string.password),
            inputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        )
        binding.etConfirmPassword.setInputFormAttributes(
            hintText = context.getString(R.string.confirm_password),
            inputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        )
    }

    fun isFieldsCorrectAndPutToBundle(): Boolean {
        val email = binding.etEmail.textInputEt
        if (!viewModel.isEmailValid(email.text.toString())) {
            email.error = context.getString(R.string.email_incorrect)
            return false
        }
        val password = binding.etPassword.textInputEt
        val confirmPassword = binding.etConfirmPassword.textInputEt

        if (!viewModel.isPasswordValid(password.text.toString(), confirmPassword.text.toString())
        ) {
            password.error = context.getString(R.string.passwords_length_warning)
            return false
        }

        if (!viewModel.isPasswordsEqual(password.text.toString(), confirmPassword.text.toString())
        ) {
            confirmPassword.error = context.getString(R.string.passwords_not_match)
            return false
        }
        viewModel.putRegistrationData(email.text.toString(), password.text.toString())
        return true
    }
}
