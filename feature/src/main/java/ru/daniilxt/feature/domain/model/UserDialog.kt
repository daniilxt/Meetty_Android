package ru.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDialog(
    val id: Long,
    val lastMessage: Message,
    val firstUser: User,
    val secondUser: User
) : Parcelable {

    fun returnCompanionUser(userId: Long): User {
        return if (userId == firstUser.id) secondUser else firstUser
    }
    fun returnMyUser(userId: Long): User {
        return if (userId != firstUser.id) secondUser else firstUser
    }
}
