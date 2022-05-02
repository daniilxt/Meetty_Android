package ru.daniilxt.feature.profile_user_education.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.profile_user_education.presentation.ProfileUserEducationViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ProfileUserEducationModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileUserEducationViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return ProfileUserEducationViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ProfileUserEducationViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ProfileUserEducationViewModel::class.java)
    }
}
