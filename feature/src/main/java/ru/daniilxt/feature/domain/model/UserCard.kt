package ru.daniilxt.feature.domain.model

import java.time.LocalDate

data class UserCard(
    val id: Long,
    val user: User,
    val userAdditionalInfo: UserAdditionalInfo
)

data class UserAdditionalInfo(
    val id: Long,
    val birthDay: LocalDate,
    val categories: List<UserCategory>
)

data class UserCategory(
    val id: Long,
    val name: String
)
