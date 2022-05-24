package ru.daniilxt.feature.di

import ru.daniilxt.common.di.FeatureApiHolder
import ru.daniilxt.common.di.FeatureContainer
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.feature.FeatureRouter
import javax.inject.Inject

@ApplicationScope
class FeatureFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val featureRouter: FeatureRouter
) : FeatureApiHolder(featureContainer) {
    override fun initializeDependencies(): Any {
        val deps = DaggerFeatureComponent_FeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerFeatureComponent.factory()
            .create(featureRouter, deps)
    }
}
