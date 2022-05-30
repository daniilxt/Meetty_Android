package ru.daniilxt.feature.user_profile.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.user_profile.presentation.UserProfileViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class UserProfileModule {

    @Provides
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return UserProfileViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): UserProfileViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(UserProfileViewModel::class.java)
    }
}
