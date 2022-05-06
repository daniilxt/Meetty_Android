package ru.daniilxt.feature.profile_personal_info.presentation

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.extensions.setInputFormAttributes
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfilePersonalInfoBinding

class InputFieldDelegate(
    private val binding: FragmentProfilePersonalInfoBinding,
    private val viewModel: ProfilePersonalInfoViewModel
) :
    BaseDelegate(binding) {
    override fun loadDelegate() {
        super.loadDelegate()
        initViews()
    }

    private fun initViews() {
        binding.etLastName.setInputFormAttributes(hintText = context.getString(R.string.last_name))
        binding.etFirstName.setInputFormAttributes(hintText = context.getString(R.string.first_name))
        binding.etBirthDay.apply {
            setInputFormAttributes(
                hintText = context.getString(R.string.birthd_day),
            )
            textInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
            textInputLayout.setEndIconTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.background_third_dark
                    )
                )
            )
            textInputLayout.setEndIconDrawable(R.drawable.ic_calendar_24)
            textInputEt.isFocusableInTouchMode = false
        }
    }

    private fun isFieldNotEmpty(field: TextInputEditText): Boolean {
        if (!viewModel.isFieldValid(field.text.toString())) {
            field.error = context.getString(R.string.field_cannot_be_empty)
            return false
        }
        return true
    }

    fun isFieldsCorrectAndPutToBundle(): Boolean {
        val firstName = binding.etFirstName.textInputEt
        if (isFieldNotEmpty(firstName)) return false

        val lastName = binding.etLastName.textInputEt
        if (isFieldNotEmpty(lastName)) return false

        val birthDay = binding.etBirthDay.textInputEt
        if (isFieldNotEmpty(birthDay)) return false

        val phoneNumber = binding.etPhoneNumber

        if (!viewModel.isPhoneNumberCorrect(phoneNumber.text.toString())) {
            firstName.error = context.getString(R.string.phone_number_warning)
            return false
        }
        //viewModel.putRegistrationData(lastName.text.toString(), lastName.text.toString())
        return true
    }
}
