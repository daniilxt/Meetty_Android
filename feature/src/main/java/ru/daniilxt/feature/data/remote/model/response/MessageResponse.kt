package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.common.extensions.toLocalDateTime
import ru.daniilxt.feature.domain.model.Message

data class MessageResponse(

    @SerializedName("id")
    val messageId: Long,

    @SerializedName("dateTime")
    val dateTime: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("reactions")
    val reactions: List<ReactionResponse> = emptyList(),

    @SerializedName("sender")
    val sender: MessageUserResponse
)

fun MessageResponse.toLastMessage() = Message(
    id = messageId,
    dateTime = dateTime.toLocalDateTime(),
    content = content,
    reactions = reactions.map { it.toReaction() },
    sender = sender.toUser(),
    isMy = false
)
