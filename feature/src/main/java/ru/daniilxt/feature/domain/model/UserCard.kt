package ru.daniilxt.feature.domain.model

data class UserCard(
    val id: Long,
    val user: SimpleUserInfo,
    val userAdditionalInfo: UserAdditionalInfo
)