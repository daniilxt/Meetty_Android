package ru.daniilxt.feature.main_screen_user_card.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.main_screen_user_card.presentation.MainScreenUserCardViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class MainScreenUserCardModule {

    @Provides
    @IntoMap
    @ViewModelKey(MainScreenUserCardViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return MainScreenUserCardViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): MainScreenUserCardViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(MainScreenUserCardViewModel::class.java)
    }
}
