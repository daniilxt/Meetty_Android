package ru.daniilxt.feature.profile_user_education.presentation

import android.widget.Toast
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
        val city = binding.spinnerCities.spinner.text.toString()
        if (city == "") {
            Toast.makeText(context, R.string.city_selection_warning, Toast.LENGTH_SHORT).show()
            return false
        }
        val institute = binding.spinnerInstitutes.spinner.text.toString()
        if (institute == "") {
            Toast.makeText(context, R.string.selection_institute_warning, Toast.LENGTH_SHORT).show()
            return false
        }
        if (viewModel.photosList.value.size == 1) {
            Toast.makeText(context, R.string.student_book_warning, Toast.LENGTH_SHORT).show()
            return false
        }
        viewModel.putEducationData(city, institute)
        return true
    }
}
