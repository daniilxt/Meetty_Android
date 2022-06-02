package ru.daniilxt.common.token

import android.content.SharedPreferences
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : TokenRepository {
    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    override fun getRefresh(): RefreshToken? {
        val token = sharedPreferences.getString(REFRESH_KEY, null)
        return if (token != null) RefreshToken(token) else null
    }

    override fun saveRefresh(refresh: String) {
        sharedPreferences.edit().putString(REFRESH_KEY, refresh).apply()
    }

    override fun clearTokens() {
        sharedPreferences.edit().putString(TOKEN_KEY, null).apply()
        sharedPreferences.edit().putString(REFRESH_KEY, null).apply()
    }

    override fun saveWelcomeStatus(status: Boolean) {
        sharedPreferences.edit().putBoolean(IS_WELCOME_WATCHED, status).apply()
    }

    override fun getWelcomeStatus(): Boolean {
        return sharedPreferences.getBoolean(IS_WELCOME_WATCHED, false)
    }

    override fun saveCurrentUserId(userId: Long) {
        sharedPreferences.edit().putLong(USER_ID_KEY, userId).apply()
    }

    override fun getCurrentUserId(): Long {
        return sharedPreferences.getLong(USER_ID_KEY, -1)
    }

    private companion object {
        private const val TOKEN_KEY = "ru.daniilxt.common.TokenRepositoryImpl.token"
        private const val REFRESH_KEY = "ru.daniilxt.common.TokenRepositoryImpl.refresh"
        private const val USER_ID_KEY = "ru.daniilxt.common.TokenRepositoryImpl.refresh"
        private const val IS_WELCOME_WATCHED: String =
            "ru.daniilxt.common.TokenRepositoryImpl.IS_WELCOME_WATCHED"
    }
}
