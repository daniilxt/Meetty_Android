package ru.daniilxt.feature.dialogs.create_achievement

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.showDialog
import ru.daniilxt.feature.R
import ru.daniilxt.feature.calendar.presentation.CalendarFragment
import ru.daniilxt.feature.databinding.DialogAchievementBinding
import ru.daniilxt.feature.domain.model.CalendarDateRange
import ru.daniilxt.feature.domain.model.CalendarSelectionMode

@SuppressLint("NewApi")
class AchievementBottomSheet(val callback: (bundle: Bundle) -> Unit) : BottomSheetDialogFragment() {

    private var _binding: DialogAchievementBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var etDelegate: InputFieldDelegate

    private val calendarFragment by lazy { CalendarFragment(CalendarSelectionMode.SINGLE_DAY) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAchievementBinding.inflate(inflater, container, false)
        dialog?.window?.setDimAmount(DIM_ALPHA)
        (dialog as BottomSheetDialog).behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        dialog?.setOnCancelListener {
            // TODO handle when dialog closed
        }

        binding.mbSave.setDebounceClickListener {
            handleSaveClick()
        }
        binding.etDate.textInputEt.setDebounceClickListener {
            parentFragmentManager.showDialog(calendarFragment)
        }
        etDelegate = InputFieldDelegate(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewDelegate(etDelegate)

        setFragmentResultListener(CalendarFragment.DATA_KEY) { _, bundle ->
            val day = bundle.getParcelable<CalendarDateRange>(CalendarFragment.DATA_KEY)
            val text = day?.startDate?.format(InputFieldDelegate.format)
            binding.etDate.textInputEt.setText(text)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog_Theme
    }

    private fun addNewDelegate(etDelegate: BaseDelegate) {
        etDelegate.loadDelegate()
    }

    private fun handleSaveClick() {
        val achievement = etDelegate.getAchievement()
        val bundle = bundleOf(
            RECEIVED_DATA_KEY to achievement
        )
        setFragmentResult(RECEIVED_DATA_KEY, bundle)
        callback(bundle)
        requireDialog().dismiss()
    }

    companion object {
        private const val DIM_ALPHA = 0.2F
        private val TAG = AchievementBottomSheet::class.java.simpleName
        val RECEIVED_DATA_KEY = "${TAG}_RECEIVED_DATA_KEY"
    }
}
