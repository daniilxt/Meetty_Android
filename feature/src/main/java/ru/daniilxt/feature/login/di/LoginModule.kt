package ru.daniilxt.feature.login.di

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
import ru.daniilxt.feature.domain.usecase.SignInUseCase
import ru.daniilxt.feature.login.presentation.LoginViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class LoginModule {

    @Provides
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter,
        signInUseCase: SignInUseCase,
        tokenRepository: TokenRepository
    ): ViewModel {
        return LoginViewModel(
            navigator, signInUseCase, tokenRepository
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): LoginViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(LoginViewModel::class.java)
    }
}
