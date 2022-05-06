package ru.daniilxt.feature.calendar_dialog.dialogs

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kizitonwose.calendarview.utils.yearMonth
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.mig35.carousellayoutmanager.CenterScrollListener
import ru.daniilxt.common.extensions.getStandaloneName
import ru.daniilxt.common.extensions.rotateView
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.calendar_dialog.dialogs.adapter.CalendarDayBinder
import ru.daniilxt.feature.calendar_dialog.dialogs.adapter.SpinnerDatePickerMonthAdapter
import ru.daniilxt.feature.calendar_dialog.dialogs.adapter.SpinnerDatePickerYearAdapter
import ru.daniilxt.feature.databinding.FragmentDatePickerDialogBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@SuppressLint("NewApi")
class DatePickerDialogFragment : DialogFragment() {
    private var _binding: FragmentDatePickerDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentCalendarMonth: YearMonth
    private var isCalendarView = true
    private lateinit var monthAdapter: SpinnerDatePickerMonthAdapter
    private lateinit var yearAdapter: SpinnerDatePickerYearAdapter
    private lateinit var yearLayoutManager: CarouselLayoutManager
    private lateinit var monthLayoutManager: CarouselLayoutManager

    private var onDestroyListener: (date: LocalDate) -> Unit = {}
    private var selectedDate: LocalDate = LocalDate.now()
    private var startDuplicationDate: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDatePickerDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(DIM_VALUE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dayBinder =
            CalendarDayBinder(
                requireContext(),
                binding.frgDatePickerCalendarView,
                selectedDate,
                startDuplicationDate
            ) {
                onDestroyListener(it)
                dismiss()
            }
        binding.frgDatePickerCalendarView.dayBinder = dayBinder
        currentCalendarMonth = selectedDate.yearMonth
        isCalendarView = true
        val firstMonth = currentCalendarMonth.minusMonths(1)
        val lastMonth = currentCalendarMonth.plusMonths(1)
        val firstDayOfWeek = DayOfWeek.MONDAY
        binding.frgDatePickerCalendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.frgDatePickerCalendarView.scrollToMonth(currentCalendarMonth)
        binding.frgDatePickerCalendarView.updateMonthRangeAsync(
            YearMonth.of(1940, 1),
            YearMonth.of(2022, 12)
        )

        binding.frgDatePickerIbNextMonth.setDebounceClickListener {
            currentCalendarMonth = currentCalendarMonth.plusMonths(1)
            binding.frgDatePickerCalendarView.smoothScrollToMonth(currentCalendarMonth)
        }

        binding.frgDatePickerIbPrevMonth.setDebounceClickListener {
            currentCalendarMonth = currentCalendarMonth.minusMonths(1)
            binding.frgDatePickerCalendarView.smoothScrollToMonth(currentCalendarMonth)
        }

        binding.frgDatePickerCalendarView.monthScrollListener = {
            val monthStr = it.yearMonth.month.getStandaloneName()
            binding.frgDatePickerTvMonthYear.text = "$monthStr ${it.year}"
            currentCalendarMonth = YearMonth.of(it.year, it.month)
        }

        binding.frgDatePickerTvMonthYear.setDebounceClickListener {
            openCalendar()
        }
        binding.frgDatePickerIvMonthYear.setDebounceClickListener {
            openCalendar()
        }
        binding.mbOk.setDebounceClickListener {
            openCalendar()
        }

        monthAdapter = SpinnerDatePickerMonthAdapter(requireContext())
        yearAdapter = SpinnerDatePickerYearAdapter()
        yearLayoutManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true)
        monthLayoutManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true)
        binding.frgDatePickerRvMonth.adapter = monthAdapter
        binding.frgDatePickerRvMonth.layoutManager = monthLayoutManager
        binding.frgDatePickerRvYear.adapter = yearAdapter
        binding.frgDatePickerRvYear.layoutManager = yearLayoutManager

        binding.frgDatePickerRvMonth.addOnScrollListener(CenterScrollListener())
        monthLayoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.frgDatePickerRvYear.addOnScrollListener(CenterScrollListener())
        yearLayoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        monthLayoutManager.scrollToPosition(monthAdapter.monthList.indexOf(LocalDate.now().month))
        yearLayoutManager.scrollToPosition(yearAdapter.yearList.indexOf(LocalDate.now().year))
    }

    private fun openCalendar() {
        isCalendarView = !isCalendarView
        if (isCalendarView) {
            binding.frgDatePickerRvMonth.visibility = View.GONE
            binding.frgDatePickerRvYear.visibility = View.GONE
            binding.frgDatePickerSnapView.visibility = View.GONE
            binding.mbOk.visibility = View.GONE
            binding.frgDatePickerCalendarView.visibility = View.VISIBLE
            binding.frgDatePickerTextviewLayout.visibility = View.VISIBLE
            binding.frgDatePickerIbPrevMonth.visibility = View.VISIBLE
            binding.frgDatePickerIbNextMonth.visibility = View.VISIBLE
            binding.frgDatePickerIvMonthYear.rotateView(90f, 0f, 100)
            setDateFromSpinner()
        } else {
            binding.frgDatePickerRvMonth.visibility = View.VISIBLE
            binding.frgDatePickerRvYear.visibility = View.VISIBLE
            binding.frgDatePickerSnapView.visibility = View.VISIBLE
            binding.mbOk.visibility = View.VISIBLE
            binding.frgDatePickerCalendarView.visibility = View.GONE
            binding.frgDatePickerTextviewLayout.visibility = View.GONE
            binding.frgDatePickerIbPrevMonth.visibility = View.GONE
            binding.frgDatePickerIbNextMonth.visibility = View.GONE
            binding.frgDatePickerIvMonthYear.rotateView(0f, 90f, 100)
            setDateToSpinner()
        }
    }

    fun setSelectedDate(date: LocalDate) {
        selectedDate = date
    }

    fun setStartDuplicationDate(date: LocalDate) {
        startDuplicationDate = date
    }

    fun setOnDestroyListener(listener: (date: LocalDate) -> Unit) {
        onDestroyListener = listener
    }

    private fun setDateToSpinner() {
        monthLayoutManager.scrollToPosition(monthAdapter.monthList.indexOf(currentCalendarMonth.month))
        yearLayoutManager.scrollToPosition(yearAdapter.yearList.indexOf(currentCalendarMonth.year))
    }

    private fun setDateFromSpinner() {
        val month = monthAdapter.monthList[monthLayoutManager.centerItemPosition]
        val year = yearAdapter.yearList[yearLayoutManager.centerItemPosition]
        currentCalendarMonth = YearMonth.of(year, month)
        binding.frgDatePickerCalendarView.scrollToMonth(YearMonth.of(year, month))
    }

    companion object {
        private const val DIM_VALUE = 0.25f
    }
}
