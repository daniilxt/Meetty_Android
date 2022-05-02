package ru.daniilxt.feature.profile_user_education.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.profile_user_education.presentation.ProfileUserEducationFragment

@Subcomponent(
    modules = [
        ProfileUserEducationModule::class,
    ]
)
@ScreenScope
interface ProfileUserEducationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ProfileUserEducationComponent
    }

    fun inject(profileUserEducationFragment: ProfileUserEducationFragment)
}
