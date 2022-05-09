package ru.daniilxt.feature.onboarding.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.onboarding.presentation.OnboardingFragment

@Subcomponent(
    modules = [
        OnboardingModule::class,
    ]
)
@ScreenScope
interface OnboardingComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): OnboardingComponent
    }

    fun inject(onboardingFragment: OnboardingFragment)
}