package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.Reaction

data class ReactionResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("emojiText")
    val emojiText: String,

    @SerializedName("usersId")
    val usersId: List<Long>
)

fun ReactionResponse.toReaction() = Reaction(
    id = id,
    emojiText = emojiText,
    usersId = usersId,
    count = usersId.count().toLong()
)
