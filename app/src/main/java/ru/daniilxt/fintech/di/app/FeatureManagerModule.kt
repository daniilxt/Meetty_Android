package ru.daniilxt.fintech.di.app

import dagger.Module
import dagger.Provides
import ru.daniilxt.common.di.FeatureApiHolder
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.fintech.di.deps.FeatureHolderManager

@Module
class FeatureManagerModule {
    @ApplicationScope
    @Provides
    fun provideFeatureHolderManager(featureApiHolderMap: @JvmSuppressWildcards Map<Class<*>, FeatureApiHolder>): FeatureHolderManager {
        return FeatureHolderManager(featureApiHolderMap)
    }
}
