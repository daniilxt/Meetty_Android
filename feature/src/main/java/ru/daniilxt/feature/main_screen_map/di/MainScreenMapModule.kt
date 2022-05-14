package ru.daniilxt.feature.main_screen_map.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.main_screen_map.presentation.MainScreenMapViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class MainScreenMapModule {

    @Provides
    @IntoMap
    @ViewModelKey(MainScreenMapViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return MainScreenMapViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): MainScreenMapViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(MainScreenMapViewModel::class.java)
    }
}
