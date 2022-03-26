package ru.daniilxt.feature.welcome_screen.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.welcome_screen.presentation.WelcomeScreenFragment

@Subcomponent(
    modules = [
        WelcomeScreenModule::class,
    ]
)
@ScreenScope
interface WelcomeScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): WelcomeScreenComponent
    }

    fun inject(welcomeScreenFragment: WelcomeScreenFragment)
}
