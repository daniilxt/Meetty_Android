package ru.daniilxt.common.token

interface TokenRepository {
    fun getToken(): String?
    fun saveToken(token: String)
    fun getRefresh(): RefreshToken?
    fun saveRefresh(refresh: String)
    fun clearTokens()
    fun saveWelcomeStatus(status: Boolean)
    fun getWelcomeStatus(): Boolean
    fun saveCurrentUserId(userId: Long)
    fun getCurrentUserId(): Long
}
