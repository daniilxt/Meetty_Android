package ru.daniilxt.feature.profile_personal_info.presentation

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
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
}
