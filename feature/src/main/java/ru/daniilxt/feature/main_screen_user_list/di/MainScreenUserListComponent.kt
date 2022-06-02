package ru.daniilxt.feature.main_screen_user_list.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.main_screen_user_list.presentation.MainScreenUserListFragment

@Subcomponent(
    modules = [
        MainScreenUserListModule::class,
    ]
)
@ScreenScope
interface MainScreenUserListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): MainScreenUserListComponent
    }

    fun inject(mainScreenUserListFragment: MainScreenUserListFragment)
}
