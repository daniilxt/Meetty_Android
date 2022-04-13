package ru.daniilxt.feature.dialog_messages.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.dialog_messages.presentation.DialogMessagesFragment

@Subcomponent(
    modules = [
        DialogMessagesModule::class,
    ]
)
@ScreenScope
interface DialogMessagesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): DialogMessagesComponent
    }

    fun inject(dialogMessagesFragment: DialogMessagesFragment)
}
