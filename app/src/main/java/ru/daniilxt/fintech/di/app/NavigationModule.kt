package ru.daniilxt.fintech.di.app

import dagger.Module
import dagger.Provides
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.fintech.navigation.Navigator
@Module
class NavigationModule {
    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @ApplicationScope
    @Provides
    fun provideSplashRouter(navigator: Navigator): FeatureRouter = navigator
}