package ru.daniilxt.feature.login.presentation

import android.text.InputType
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.extensions.setInputFormAttributes
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentLoginBinding

class InputFieldDelegate(
    private val binding: FragmentLoginBinding,
    private val viewModel: LoginViewModel
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
    }

    fun checkFieldsAndSignIn(): Boolean {
        val email = binding.etEmail.textInputEt
        if (!viewModel.isEmailValid(email.text.toString())) {
            email.error = context.getString(R.string.email_incorrect)
            return false
        }
        val password = binding.etPassword.textInputEt

        if (!viewModel.isFieldCorrect(password.text.toString())
        ) {
            password.error = context.getString(R.string.field_cannot_be_empty)
            return false
        }
        viewModel.login(email.text.toString(), password.text.toString())
        return true
    }
}
