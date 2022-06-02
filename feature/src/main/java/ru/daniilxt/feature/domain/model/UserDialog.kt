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

    fun returnCompanionUser(user: User): User {
        return if (user.id == firstUser.id) secondUser else firstUser
    }
}
