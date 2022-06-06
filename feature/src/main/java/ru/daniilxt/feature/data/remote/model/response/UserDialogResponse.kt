package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.UserDialog

data class UserDialogResponse(
    @SerializedName("id")
    val dialogId: Long,

    @SerializedName("lastMessage")
    val lastMessage: MessageResponse,

    @SerializedName("firstUser")
    val firstUser: MessageUserResponse,

    @SerializedName("secondUser")
    val secondUser: MessageUserResponse
)

fun UserDialogResponse.toUserDialog(currentUserId: Long) = UserDialog(
    id = dialogId,
    lastMessage = lastMessage.toChatMessage(currentUserId),
    firstUser = firstUser.toUser(),
    secondUser = secondUser.toUser()
)
