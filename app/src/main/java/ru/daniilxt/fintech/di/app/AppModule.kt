package ru.daniilxt.fintech.di.app

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.fintech.App

@Module
class AppModule {

    @Provides
    @ApplicationScope
    fun provideContext(application: App): Context {
        return application
    }
}