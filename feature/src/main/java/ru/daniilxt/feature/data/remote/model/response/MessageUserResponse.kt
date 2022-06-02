package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.User

data class MessageUserResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("avatarLink")
    val avatarLink: String,

    @SerializedName("sex")
    val sex: String
)

fun MessageUserResponse.toUser() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    avatarLink = avatarLink
)
