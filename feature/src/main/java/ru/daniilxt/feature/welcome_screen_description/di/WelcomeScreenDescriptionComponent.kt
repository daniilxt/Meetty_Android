package ru.daniilxt.feature.welcome_screen_description.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.welcome_screen_description.presentation.WelcomeScreenDescriptionFragment

@Subcomponent(
    modules = [
        WelcomeScreenDescriptionModule::class,
    ]
)
@ScreenScope
interface WelcomeScreenDescriptionComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): WelcomeScreenDescriptionComponent
    }

    fun inject(welcomeScreenDescriptionFragment: WelcomeScreenDescriptionFragment)
}
