package ru.daniilxt.common.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.common.token.TokenRepository
import ru.daniilxt.common.token.TokenRepositoryImpl
import ru.daniilxt.common.utils.BundleDataWrapper

const val SHARED_PREFERENCES_FILE = "meetty.preferences"

@Module
abstract class CommonModule {
    companion object {
        @Provides
        @ApplicationScope
        fun provideBundleDataWrapper(): BundleDataWrapper {
            return BundleDataWrapper()
        }

        @Provides
        @ApplicationScope
        fun provideSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
        }
    }

    @Binds
    abstract fun provideTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository
}
