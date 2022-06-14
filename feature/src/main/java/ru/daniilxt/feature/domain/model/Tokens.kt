package ru.daniilxt.feature.domain.model

data class Tokens(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long = -1
)
