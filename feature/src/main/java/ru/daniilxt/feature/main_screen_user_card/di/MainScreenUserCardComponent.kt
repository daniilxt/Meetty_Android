package ru.daniilxt.feature.main_screen_user_card.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.main_screen_user_card.presentation.MainScreenUserCardFragment

@Subcomponent(
    modules = [
        MainScreenUserCardModule::class,
    ]
)
@ScreenScope
interface MainScreenUserCardComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): MainScreenUserCardComponent
    }

    fun inject(mainScreenUserCardFragment: MainScreenUserCardFragment)
}
