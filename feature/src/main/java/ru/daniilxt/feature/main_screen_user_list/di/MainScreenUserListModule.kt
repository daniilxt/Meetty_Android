package ru.daniilxt.feature.main_screen_user_list.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.main_screen_user_list.presentation.MainScreenUserListViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class MainScreenUserListModule {

    @Provides
    @IntoMap
    @ViewModelKey(MainScreenUserListViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return MainScreenUserListViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): MainScreenUserListViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(MainScreenUserListViewModel::class.java)
    }
}
