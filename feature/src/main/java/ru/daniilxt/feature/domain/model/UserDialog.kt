package ru.daniilxt.feature.domain.model

data class UserDialog(
    val id: Long,
    val lastMessage: Message,
    val firstUser: User,
    val secondUser: User
) {

    fun returnCompanionUser(user: User): User {
        return if (user.id == firstUser.id) secondUser else firstUser
    }
}