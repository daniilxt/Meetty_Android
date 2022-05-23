package ru.daniilxt.feature.domain.model

import ru.daniilxt.feature.data.remote.model.body.UserCredentialsBody

data class UserCredentials(
    val email: String,
    val password: String
)

fun UserCredentials.toUserCredentialsBody() = UserCredentialsBody(
    email = email,
    password = password
)