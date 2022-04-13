package ru.daniilxt.feature.dialog_messages.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.dialog_messages.presentation.DialogMessagesViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class DialogMessagesModule {

    @Provides
    @IntoMap
    @ViewModelKey(DialogMessagesViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return DialogMessagesViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): DialogMessagesViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(DialogMessagesViewModel::class.java)
    }
}
