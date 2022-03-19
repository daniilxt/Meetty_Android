package ru.daniilxt.feature.main_screen.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.main_screen.presentation.MainScreenViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class MainScreenModule {

    @Provides
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return MainScreenViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): MainScreenViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(MainScreenViewModel::class.java)
    }
}