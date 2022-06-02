package ru.daniilxt.feature.profile_professional_interests.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.profile_professional_interests.presentation.ProfileProfessionalInterestsFragment

@Subcomponent(
    modules = [
        ProfileProfessionalInterestsModule::class,
    ]
)
@ScreenScope
interface ProfileProfessionalInterestsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ProfileProfessionalInterestsComponent
    }

    fun inject(profileProfessionalInterestsFragment: ProfileProfessionalInterestsFragment)
}
