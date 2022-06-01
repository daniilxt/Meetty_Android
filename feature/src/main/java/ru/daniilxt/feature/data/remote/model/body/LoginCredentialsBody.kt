package ru.daniilxt.feature.data.remote.model.body

import com.google.gson.annotations.SerializedName

data class LoginCredentialsBody(
    @SerializedName("login")
    val email: String,
    @SerializedName("password")
    val password: String
)