package ru.daniilxt.feature.profile_steps.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.profile_steps.presentation.ProfileStepsFragment

@Subcomponent(
    modules = [
        ProfileStepsModule::class,
    ]
)
@ScreenScope
interface ProfileStepsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ProfileStepsComponent
    }

    fun inject(profileStepsFragment: ProfileStepsFragment)
}
