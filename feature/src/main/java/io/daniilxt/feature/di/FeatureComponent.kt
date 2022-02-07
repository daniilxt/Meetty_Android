package io.daniilxt.feature.di

import dagger.BindsInstance
import dagger.Component
import io.daniilxt.common.di.CommonApi
import io.daniilxt.common.di.scope.FeatureScope
import io.daniilxt.feature.FeatureRouter
import io.daniilxt.feature.main_screen.di.MainScreenComponent

@Component(
    dependencies = [
        FeatureDependencies::class,
    ],
    modules = [
        FeatureModule::class,
        FeatureDataModule::class
    ]
)
@FeatureScope
interface FeatureComponent {

    fun mainScreenComponentFactory(): MainScreenComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance featureRouter: FeatureRouter,
            deps: FeatureDependencies
        ): FeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
        ]
    )
    interface FeatureDependenciesComponent : FeatureDependencies
}