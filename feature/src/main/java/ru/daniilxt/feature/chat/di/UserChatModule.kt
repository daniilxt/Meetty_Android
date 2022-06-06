package ru.daniilxt.feature.chat.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.common.token.TokenRepository
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.chat.presentation.UserChatViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class UserChatModule {

    @Provides
    @IntoMap
    @ViewModelKey(UserChatViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter,
        tokenRepository: TokenRepository
    ): ViewModel {
        return UserChatViewModel(
            navigator, tokenRepository
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): UserChatViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(UserChatViewModel::class.java)
    }
}
