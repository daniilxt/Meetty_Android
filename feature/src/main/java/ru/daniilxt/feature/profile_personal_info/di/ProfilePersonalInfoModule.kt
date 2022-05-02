package ru.daniilxt.feature.profile_personal_info.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.profile_personal_info.presentation.ProfilePersonalInfoViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ProfilePersonalInfoModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfilePersonalInfoViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return ProfilePersonalInfoViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ProfilePersonalInfoViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ProfilePersonalInfoViewModel::class.java)
    }
}
