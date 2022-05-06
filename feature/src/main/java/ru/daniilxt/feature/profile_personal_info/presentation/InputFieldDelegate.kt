package ru.daniilxt.feature.profile_personal_info.presentation

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.extensions.setInputFormAttributes
import ru.daniilxt.feature.R
import ru.daniilxt.feature.data_wrapper.UserPersonalInfo
import ru.daniilxt.feature.databinding.FragmentProfilePersonalInfoBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
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
        if (!isFieldNotEmpty(firstName)) return false

        val lastName = binding.etLastName.textInputEt
        if (!isFieldNotEmpty(lastName)) return false

        val birthDay = binding.etBirthDay.textInputEt
        if (!viewModel.isFieldValid(birthDay.text.toString())) {
            Toast.makeText(context, R.string.field_cannot_be_empty, Toast.LENGTH_SHORT).show()
            return false
        }

        val phoneNumber = binding.etPhoneNumber
        if (!viewModel.isPhoneNumberCorrect(phoneNumber.rawText)) {
            Toast.makeText(context, R.string.phone_number_warning, Toast.LENGTH_SHORT).show()
            return false
        }

        viewModel.putRegistrationData(
            UserPersonalInfo(
                firstName = firstName.text.toString(),
                lastName = lastName.text.toString(),
                birthDay = LocalDate.parse(birthDay.text.toString(), format),
                phoneNumber = phoneNumber.text.toString(),
                sex = getActiveButtonText()
            )
        )
        return true
    }

    private fun getActiveButtonText() = with(binding) {
        if (mbMan.isChecked) mbMan.text.toString() else mbWoman.text.toString()
    }

    companion object {
        val format: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    }
}
