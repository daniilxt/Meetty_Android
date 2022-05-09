package ru.daniilxt.feature.welcome_screen.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.welcome_screen.presentation.WelcomeScreenViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class WelcomeScreenModule {

    @Provides
    @IntoMap
    @ViewModelKey(WelcomeScreenViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return WelcomeScreenViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): WelcomeScreenViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(WelcomeScreenViewModel::class.java)
    }
}
