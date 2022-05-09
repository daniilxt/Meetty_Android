package ru.daniilxt.feature.calendar.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.calendar.presentation.CalendarFragment

@Subcomponent(
    modules = [
        CalendarModule::class,
    ]
)
@ScreenScope
interface CalendarComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): CalendarComponent
    }

    fun inject(calendarFragment: CalendarFragment)
}
