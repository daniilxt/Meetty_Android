package ru.daniilxt.common.di.modules

import dagger.Module
import dagger.Provides
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.common.utils.BundleDataWrapper

@Module
abstract class CommonModule {
    companion object {
        @Provides
        @ApplicationScope
        fun provideBundleDataWrapper(): BundleDataWrapper {
            return BundleDataWrapper()
        }
    }
}
