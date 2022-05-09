package ru.daniilxt.meetty.root.di

import ru.daniilxt.common.di.FeatureApiHolder
import ru.daniilxt.common.di.FeatureContainer
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.meetty.navigation.Navigator
import javax.inject.Inject

@ApplicationScope
class RootFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val navigator: Navigator
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val rootFeatureDependencies = DaggerRootComponent_RootFeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerRootComponent.factory()
            .create(navigator, rootFeatureDependencies)
    }
}
