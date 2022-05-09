package ru.daniilxt.feature.welcome_screen_description.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.welcome_screen_description.presentation.WelcomeScreenDescriptionViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class WelcomeScreenDescriptionModule {

    @Provides
    @IntoMap
    @ViewModelKey(WelcomeScreenDescriptionViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return WelcomeScreenDescriptionViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): WelcomeScreenDescriptionViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(WelcomeScreenDescriptionViewModel::class.java)
    }
}
