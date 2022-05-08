package ru.daniilxt.feature.profile_professional_interests.di

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
import ru.daniilxt.feature.profile_professional_interests.presentation.ProfileProfessionalInterestsViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ProfileProfessionalInterestsModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileProfessionalInterestsViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter,
        dataWrapper: ProfileDataWrapper
    ): ViewModel {
        return ProfileProfessionalInterestsViewModel(
            navigator, dataWrapper
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ProfileProfessionalInterestsViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(
            ProfileProfessionalInterestsViewModel::class.java
        )
    }
}
