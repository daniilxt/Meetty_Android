package ru.daniilxt.feature.profile_steps.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.profile_steps.presentation.ProfileStepsViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ProfileStepsModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileStepsViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return ProfileStepsViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ProfileStepsViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ProfileStepsViewModel::class.java)
    }
}