package ru.daniilxt.feature.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.daniilxt.common.di.scope.FeatureScope
import ru.daniilxt.feature.navigation.MainScreenNavigator
import ru.daniilxt.feature.navigation.interfaces.MainScreenRouter

@Module
abstract class MainScreenNavigationModule {
    @Binds
    abstract fun bindsMainScreenRouter(mainScreenNavigator: MainScreenNavigator): MainScreenRouter

    companion object {
        @Provides
        @FeatureScope
        fun provideMainScreenNavigator(): MainScreenNavigator {
            return MainScreenNavigator()
        }
    }
}
