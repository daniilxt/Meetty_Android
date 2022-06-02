package ru.daniilxt.meetty.di.app

import dagger.BindsInstance
import dagger.Component
import ru.daniilxt.common.di.CommonApi
import ru.daniilxt.common.di.modules.CommonModule
import ru.daniilxt.common.di.modules.NetworkModule
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.meetty.App
import ru.daniilxt.meetty.di.deps.ComponentHolderModule

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        CommonModule::class,
        NetworkModule::class,
        ComponentHolderModule::class,
        FeatureManagerModule::class,
        NavigationModule::class
    ]
)
interface AppComponent : CommonApi {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
