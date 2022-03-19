package ru.daniilxt.fintech.root.presentation.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.fintech.root.presentation.MainActivityViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class RootActivityModule {

    @Provides
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun provideViewModel(): ViewModel {
        return MainActivityViewModel()
    }

    @Provides
    fun provideViewModelCreator(
        activity: AppCompatActivity,
        viewModelFactory: ViewModelProvider.Factory
    ): MainActivityViewModel {
        return ViewModelProvider(activity, viewModelFactory).get(MainActivityViewModel::class.java)
    }
}
