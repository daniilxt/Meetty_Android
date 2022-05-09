package ru.daniilxt.feature.profile_personal_info.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.profile_personal_info.presentation.ProfilePersonalInfoFragment

@Subcomponent(
    modules = [
        ProfilePersonalInfoModule::class,
    ]
)
@ScreenScope
interface ProfilePersonalInfoComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ProfilePersonalInfoComponent
    }

    fun inject(profilePersonalInfoFragment: ProfilePersonalInfoFragment)
}
