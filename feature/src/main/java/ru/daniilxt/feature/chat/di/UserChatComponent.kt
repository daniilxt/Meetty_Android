package ru.daniilxt.feature.chat.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.chat.presentation.UserChatFragment

@Subcomponent(
    modules = [
        UserChatModule::class,
    ]
)
@ScreenScope
interface UserChatComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): UserChatComponent
    }

    fun inject(userChatFragment: UserChatFragment)
}
