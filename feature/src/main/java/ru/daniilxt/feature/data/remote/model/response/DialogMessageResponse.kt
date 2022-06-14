package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class DialogMessageResponse(

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
