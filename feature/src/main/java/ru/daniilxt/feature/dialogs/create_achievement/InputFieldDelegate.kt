package ru.daniilxt.feature.dialogs.create_achievement

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.extensions.setInputFormAttributes
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.DialogAchievementBinding
import ru.daniilxt.feature.domain.model.UserAchievement
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
class InputFieldDelegate(
    private val binding: DialogAchievementBinding,
) :
    BaseDelegate(binding) {
    override fun loadDelegate() {
        super.loadDelegate()
        initViews()
        setSaveButtonClickability()
    }

    private fun setSaveButtonClickability() {
        binding.mbSave.isEnabled = false

        addTextWatcher(binding.etTitle.textInputEt, binding.mbSave)
        addTextWatcher(binding.etDate.textInputEt, binding.mbSave)
        addTextWatcher(binding.etDescription.textInputEt, binding.mbSave)
    }

    private fun addTextWatcher(et: TextInputEditText, btn: MaterialButton) {
        et.addTextChangedListener {
            btn.isEnabled = !binding.etTitle.textInputEt.text.isNullOrBlank() &&
                !binding.etDate.textInputEt.text.isNullOrBlank() &&
                !binding.etDescription.textInputEt.text.isNullOrBlank()
        }
    }

    private fun initViews() {
        binding.etTitle.textInputEt.post {
            binding.etTitle.textInputEt.setText("")
        }
        binding.etDate.textInputEt.post {
            binding.etDate.textInputEt.setText("")
        }
        binding.etDescription.textInputEt.post {
            binding.etDescription.textInputEt.setText("")
        }

        binding.etTitle.setInputFormAttributes(
            hintText = context.getString(R.string.naming),
            textLength = 200
        )
        binding.etDescription.setInputFormAttributes(
            hintText = context.getString(R.string.description),
            textLength = 1000
        )
        binding.etDescription.textInputEt.apply {
            isSingleLine = false
            imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
            setLines(10)
            gravity = Gravity.START
        }

        binding.etDate.apply {
            setInputFormAttributes(
                hintText = context.getString(R.string.date),
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

    fun getAchievement(): UserAchievement {
        return UserAchievement(
            title = binding.etTitle.textInputEt.text.toString(),
            date = LocalDate.parse(binding.etDate.textInputEt.text.toString(), format),
            achievementDescription = binding.etDescription.textInputEt.text.toString(),
        )
    }

    companion object {
        val format: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    }
}
