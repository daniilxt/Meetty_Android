package ru.daniilxt.feature.calendar.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.common.utils.BundleDataWrapper
import ru.daniilxt.feature.calendar.presentation.CalendarViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class CalendarModule {

    @Provides
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    fun provideViewModel(dataWrapper: BundleDataWrapper): ViewModel {
        return CalendarViewModel(dataWrapper)
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): CalendarViewModel {
        return ViewModelProvider(
            fragment,
            viewModelFactory
        ).get(CalendarViewModel::class.java)
    }
}
