package ru.daniilxt.feature.profile_personal_info.presentation

import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setInputFormAttributes
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfilePersonalInfoBinding

class InputFieldDelegate(private val binding: FragmentProfilePersonalInfoBinding) :
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
            textInputLayout.setEndIconDrawable(R.drawable.ic_calendar_24)
            textInputEt.isFocusableInTouchMode = false
            textInputEt.setDebounceClickListener {
                Toast.makeText(context, "dkfk", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
