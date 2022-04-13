package ru.daniilxt.feature.di

import dagger.BindsInstance
import dagger.Component
import ru.daniilxt.common.di.CommonApi
import ru.daniilxt.common.di.scope.FeatureScope
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.chat.di.UserChatComponent
import ru.daniilxt.feature.login.di.LoginComponent
import ru.daniilxt.feature.main_screen.di.MainScreenComponent
import ru.daniilxt.feature.onboarding.di.OnboardingComponent
import ru.daniilxt.feature.user_dialogs.di.UserDialogsComponent
import ru.daniilxt.feature.welcome_screen.di.WelcomeScreenComponent
import ru.daniilxt.feature.welcome_screen_description.di.WelcomeScreenDescriptionComponent

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
    fun onboardingComponentFactory(): OnboardingComponent.Factory
    fun welcomeScreenComponentFactory(): WelcomeScreenComponent.Factory
    fun welcomeScreenDescriptionComponentFactory(): WelcomeScreenDescriptionComponent.Factory
    fun loginComponentFactory(): LoginComponent.Factory
    fun userDialogsComponentFactory(): UserDialogsComponent.Factory
    fun userChatComponentFactory(): UserChatComponent.Factory

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
