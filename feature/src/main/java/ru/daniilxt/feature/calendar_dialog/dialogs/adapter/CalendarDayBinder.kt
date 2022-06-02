package ru.daniilxt.feature.calendar_dialog.dialogs.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import ru.daniilxt.feature.R
import java.time.LocalDate

@SuppressLint("NewApi")
class CalendarDayBinder(
    val context: Context,
    val calendarView: CalendarView,
    var selectedDate: LocalDate,
    val startDuplicationDate: LocalDate?,
    val onDateSelectedListener: (date: LocalDate) -> Unit
) : DayBinder<DayViewContainer> {

    override fun bind(container: DayViewContainer, day: CalendarDay) {
        container.day = day
        container.binding.calendarDayText.text = day.date.dayOfMonth.toString()

        with(container.binding.calendarDayText) {
            if (day.owner == DayOwner.THIS_MONTH) {
                container.binding.calendarDayText.visibility = View.VISIBLE
                if (day.date == selectedDate) {
                    setTextColor(ContextCompat.getColor(context, R.color.text_color_selected_date))
                    setBackgroundResource(R.drawable.background_calendar_selection)
                } else {

                    if (day.date == LocalDate.now()) {
                        setTextColor(ContextCompat.getColor(context, R.color.text_color_primary))
                        if (startDuplicationDate != null && day.date.isBefore(startDuplicationDate)) {
                            container.view.isClickable = false
                        }
                    } else if (startDuplicationDate != null && day.date.isBefore(
                            startDuplicationDate
                        )
                    ) {
                        setTextColor(ContextCompat.getColor(context, R.color.text_color_calendar))
                        container.view.isClickable = false
                    } else {
                        setTextColor(ContextCompat.getColor(context, R.color.black))
                    }
                    background = null
                }
            } else {
                container.binding.calendarDayText.visibility = View.INVISIBLE
            }
        }
    }

    override fun create(view: View) = DayViewContainer(view) {
        val currentSelection = selectedDate
        if (currentSelection != it) {
            selectedDate = it
            calendarView.notifyDateChanged(it)
            calendarView.notifyDateChanged(currentSelection)
            onDateSelectedListener(selectedDate)
        }
    }
}
