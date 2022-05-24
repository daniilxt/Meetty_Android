package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.Tokens

data class TokensResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)

fun TokensResponse.toTokens() = Tokens(
    accessToken = accessToken,
    refreshToken = refreshToken
)
