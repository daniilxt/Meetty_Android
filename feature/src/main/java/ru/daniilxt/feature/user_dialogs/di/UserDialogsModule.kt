package ru.daniilxt.feature.user_dialogs.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.user_dialogs.presentation.UserDialogsViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class UserDialogsModule {

    @Provides
    @IntoMap
    @ViewModelKey(UserDialogsViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return UserDialogsViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): UserDialogsViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(UserDialogsViewModel::class.java)
    }
}
