package ru.daniilxt.feature.onboarding.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.onboarding.presentation.OnboardingViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class OnboardingModule {

    @Provides
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return OnboardingViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): OnboardingViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(OnboardingViewModel::class.java)
    }
}