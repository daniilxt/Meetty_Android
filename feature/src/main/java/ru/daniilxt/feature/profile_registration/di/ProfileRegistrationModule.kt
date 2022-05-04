package ru.daniilxt.feature.profile_registration.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import ru.daniilxt.feature.profile_registration.presentation.ProfileRegistrationViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ProfileRegistrationModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileRegistrationViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter,
        dataWrapper: ProfileDataWrapper
    ): ViewModel {
        return ProfileRegistrationViewModel(
            navigator,
            dataWrapper
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ProfileRegistrationViewModel {
        return ViewModelProvider(
            fragment,
            viewModelFactory
        ).get(ProfileRegistrationViewModel::class.java)
    }
}
