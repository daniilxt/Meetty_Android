package ru.daniilxt.feature.profile_personal_info.presentation

import ru.daniilxt.common.extensions.setInputFormAttributes
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfilePersonalInfoBinding

class InputFieldDelegate(private val binding: FragmentProfilePersonalInfoBinding) : BaseDelegate() {
    private val context get() = binding.root.context
    override fun loadDelegate() {
        super.loadDelegate()
        initViews()
    }

    private fun initViews() {
        binding.etLastName.setInputFormAttributes(hintText = context.getString(R.string.last_name))
        binding.etFirstName.setInputFormAttributes(hintText = context.getString(R.string.first_name))
    }
}

abstract class BaseDelegate {
    open fun loadDelegate() {}
}
