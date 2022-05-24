package ru.daniilxt.feature.main_screen_map.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.main_screen_map.presentation.MainScreenMapFragment

@Subcomponent(
    modules = [
        MainScreenMapModule::class,
    ]
)
@ScreenScope
interface MainScreenMapComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): MainScreenMapComponent
    }

    fun inject(mainScreenMapFragment: MainScreenMapFragment)
}
