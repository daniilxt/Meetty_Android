package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.SimpleUserInfo

data class SimpleUserResponse(
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

fun SimpleUserResponse.toSimpleUser() = SimpleUserInfo(
    id = id,
    firstName = firstName,
    lastName = lastName,
    avatarUri = avatarLink.ifEmpty { "https://sun9-west.userapi.com/sun9-50/s/v1/if2/NTo9XA_NKv1OIl1J12FYJcNpKHPOLmWblmIFWS-BymD33CRS8GCybjFlLan2qbN-QldyPhvc5_aaq9sFhqR22K9D.jpg?size=604x453&quality=95&type=album" },
    sex = sex,
)
