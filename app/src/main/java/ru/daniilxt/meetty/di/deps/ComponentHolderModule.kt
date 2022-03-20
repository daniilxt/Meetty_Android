package ru.daniilxt.meetty.di.deps

import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.FeatureApiHolder
import ru.daniilxt.common.di.FeatureContainer
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureFeatureHolder
import ru.daniilxt.meetty.App
import ru.daniilxt.meetty.root.di.RootApi
import ru.daniilxt.meetty.root.di.RootFeatureHolder

@Module
interface ComponentHolderModule {

    @ApplicationScope
    @Binds
    fun provideFeatureContainer(application: App): FeatureContainer

    @ApplicationScope
    @Binds
    @ClassKey(RootApi::class)
    @IntoMap
    fun provideMainFeature(rootFeatureHolder: RootFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(FeatureApi::class)
    @IntoMap
    fun provideFeatureFeature(featureHolder: FeatureFeatureHolder): FeatureApiHolder
}