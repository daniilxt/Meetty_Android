package ru.daniilxt.feature.profile_registration.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.profile_registration.presentation.ProfileRegistrationFragment

@Subcomponent(
    modules = [
        ProfileRegistrationModule::class,
    ]
)
@ScreenScope
interface ProfileRegistrationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ProfileRegistrationComponent
    }

    fun inject(profileRegistrationFragment: ProfileRegistrationFragment)
}
