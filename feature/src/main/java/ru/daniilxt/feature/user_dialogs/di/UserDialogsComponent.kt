package ru.daniilxt.feature.user_dialogs.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.user_dialogs.presentation.UserDialogsFragment

@Subcomponent(
    modules = [
        UserDialogsModule::class,
    ]
)
@ScreenScope
interface UserDialogsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): UserDialogsComponent
    }

    fun inject(userDialogsFragment: UserDialogsFragment)
}
