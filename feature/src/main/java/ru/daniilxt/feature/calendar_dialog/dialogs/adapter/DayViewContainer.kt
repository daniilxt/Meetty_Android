package ru.daniilxt.feature.calendar_dialog.dialogs.adapter

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import ru.daniilxt.feature.databinding.IncludeDatePickerDayLayoutBinding
import java.time.LocalDate

class DayViewContainer(view: View, clickedDay: (day: LocalDate) -> Unit) : ViewContainer(view) {
    val binding = IncludeDatePickerDayLayoutBinding.bind(view)
    lateinit var day: CalendarDay // Will be set when this container is bound.

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                clickedDay(day.date)
            }
        }
    }
}
