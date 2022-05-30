package ru.daniilxt.feature.user_profile.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.user_profile.presentation.UserProfileFragment

@Subcomponent(
    modules = [
        UserProfileModule::class,
    ]
)
@ScreenScope
interface UserProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): UserProfileComponent
    }

    fun inject(userProfileFragment: UserProfileFragment)
}
