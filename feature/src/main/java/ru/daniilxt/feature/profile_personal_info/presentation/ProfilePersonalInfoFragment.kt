package ru.daniilxt.feature.profile_personal_info.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.calendar.presentation.CalendarFragment
import ru.daniilxt.feature.calendar_dialog.dialogs.DatePickerDialogFragment
import ru.daniilxt.feature.databinding.FragmentProfilePersonalInfoBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.domain.model.CalendarSelectionMode
import ru.daniilxt.feature.profile_steps.presentation.adapter.IValidateFragmentFields
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
class ProfilePersonalInfoFragment :
    BaseFragment<ProfilePersonalInfoViewModel>(R.layout.fragment_profile_personal_info),
    IValidateFragmentFields {

    override val binding: FragmentProfilePersonalInfoBinding by viewBinding(
        FragmentProfilePersonalInfoBinding::bind
    )
    private val etDelegate by lazy {
        InputFieldDelegate(binding, viewModel)
    }
    private val calendarFragment by lazy { CalendarFragment(CalendarSelectionMode.SINGLE_DAY) }
    private val datePickerDialog: DatePickerDialogFragment by lazy {
        DatePickerDialogFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
    }

    override fun setupViews() {
        super.setupViews()
        addNewDelegate(etDelegate)
        setButtons()
    }

    private fun setButtons() {
        binding.etBirthDay.textInputEt.setDebounceClickListener {
            datePickerDialog.setSelectedDate(LocalDate.now())
            datePickerDialog.setOnDestroyListener { date ->
                binding.etBirthDay.textInputEt.setText(
                    date.format(
                        DateTimeFormatter.ofPattern(
                            DATE_PATTERN
                        )
                    )
                )
            }
            datePickerDialog.show(parentFragmentManager, DATE_DIALOG_TAG)
        }
    }

    private fun addNewDelegate(etDelegate: BaseDelegate) {
        etDelegate.loadDelegate()
    }

    override fun isFieldsFilled(callback: (isFilled: Boolean) -> Unit) {
        callback(true)
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profilePersonalInfoComponentFactory()
            .create(this)
            .inject(this)
    }

    companion object {
        private const val DATE_DIALOG_TAG = "DATE_DIALOG_TAG"
        private const val DATE_PATTERN = "dd.MM.yyyy"
        fun newInstance(): ProfilePersonalInfoFragment {
            return ProfilePersonalInfoFragment()
        }
    }
}
