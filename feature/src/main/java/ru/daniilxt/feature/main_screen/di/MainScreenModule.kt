package ru.daniilxt.feature.main_screen.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.usecase.GetProfileUserInfoUseCase
import ru.daniilxt.feature.main_screen.presentation.MainScreenViewModel
import ru.daniilxt.feature.navigation.interfaces.MainScreenRouter

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class MainScreenModule {

    @Provides
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter,
        mainScreenRouter: MainScreenRouter,
        gerUserProfileInfoUseCase: GetProfileUserInfoUseCase
    ): ViewModel {
        return MainScreenViewModel(
            navigator, mainScreenRouter, gerUserProfileInfoUseCase
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): MainScreenViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(MainScreenViewModel::class.java)
    }
}
