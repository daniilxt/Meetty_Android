package ru.daniilxt.feature.profile_steps.presentation.adapter

interface IValidateFragmentFields {
    fun isFieldsFilled(callback: (isFilled: Boolean) -> Unit)
}
