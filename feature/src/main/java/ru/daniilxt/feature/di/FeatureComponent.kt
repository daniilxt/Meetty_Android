package ru.daniilxt.feature.di

import dagger.BindsInstance
import dagger.Component
import ru.daniilxt.common.di.CommonApi
import ru.daniilxt.common.di.scope.FeatureScope
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.calendar.di.CalendarComponent
import ru.daniilxt.feature.login.di.LoginComponent
import ru.daniilxt.feature.main_screen.di.MainScreenComponent
import ru.daniilxt.feature.main_screen_map.di.MainScreenMapComponent
import ru.daniilxt.feature.main_screen_user_card.di.MainScreenUserCardComponent
import ru.daniilxt.feature.main_screen_user_list.di.MainScreenUserListComponent
import ru.daniilxt.feature.onboarding.di.OnboardingComponent
import ru.daniilxt.feature.profile_personal_info.di.ProfilePersonalInfoComponent
import ru.daniilxt.feature.profile_professional_interests.di.ProfileProfessionalInterestsComponent
import ru.daniilxt.feature.profile_registration.di.ProfileRegistrationComponent
import ru.daniilxt.feature.profile_steps.di.ProfileStepsComponent
import ru.daniilxt.feature.profile_user_achievements.di.ProfileUserAchievementsComponent
import ru.daniilxt.feature.profile_user_education.di.ProfileUserEducationComponent
import ru.daniilxt.feature.welcome_screen.di.WelcomeScreenComponent
import ru.daniilxt.feature.welcome_screen_description.di.WelcomeScreenDescriptionComponent

@Component(
    dependencies = [
        FeatureDependencies::class,
    ],
    modules = [
        FeatureModule::class,
        FeatureDataModule::class,
        MainScreenNavigationModule::class
    ]
)
@FeatureScope
interface FeatureComponent {

    fun mainScreenComponentFactory(): MainScreenComponent.Factory
    fun mainScreenMapComponentFactory(): MainScreenMapComponent.Factory
    fun mainScreenUserCardComponentFactory(): MainScreenUserCardComponent.Factory
    fun mainScreenUserListComponentFactory(): MainScreenUserListComponent.Factory

    fun onboardingComponentFactory(): OnboardingComponent.Factory
    fun welcomeScreenComponentFactory(): WelcomeScreenComponent.Factory
    fun welcomeScreenDescriptionComponentFactory(): WelcomeScreenDescriptionComponent.Factory
    fun loginComponentFactory(): LoginComponent.Factory
    fun profileStepsComponentFactory(): ProfileStepsComponent.Factory
    fun profilePersonalInfoComponentFactory(): ProfilePersonalInfoComponent.Factory
    fun profileUserAchievementsComponentFactory(): ProfileUserAchievementsComponent.Factory
    fun profileUserEducationComponentFactory(): ProfileUserEducationComponent.Factory
    fun profileRegistrationComponentFactory(): ProfileRegistrationComponent.Factory
    fun calendarComponentFactory(): CalendarComponent.Factory
    fun profileProfessionalInterestsComponentFactory(): ProfileProfessionalInterestsComponent.Factory

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
