package ru.daniilxt.feature.login.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.login.presentation.LoginFragment

@Subcomponent(
    modules = [
        LoginModule::class,
    ]
)
@ScreenScope
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): LoginComponent
    }

    fun inject(loginFragment: LoginFragment)
}
