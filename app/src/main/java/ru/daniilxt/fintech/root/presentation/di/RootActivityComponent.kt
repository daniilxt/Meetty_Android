package ru.daniilxt.fintech.root.presentation.di

import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.fintech.root.presentation.MainActivity

@Subcomponent(
    modules = [
        RootActivityModule::class
    ]
)
@ScreenScope
interface RootActivityComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance activity: AppCompatActivity
        ): RootActivityComponent
    }

    fun inject(rootActivity: MainActivity)
}
