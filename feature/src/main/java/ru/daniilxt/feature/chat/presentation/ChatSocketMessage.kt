package ru.daniilxt.feature.chat.presentation

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.daniilxt.feature.domain.model.ChatMessage
import ru.daniilxt.feature.domain.model.Reaction
import ru.daniilxt.feature.domain.model.User
import java.time.LocalDateTime

@SuppressLint("NewApi")
@Parcelize
data class ChatSocketMessage(
    val id: Long = System.currentTimeMillis(),
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val content: String = "",
    val reactions: List<Reaction> = emptyList(),
    val sender: User,
    val receiver: User,
) : Parcelable

fun ChatSocketMessage.toMessage(currentUserId: Long): ChatMessage {
    return ChatMessage(
        id = this.id,
        dateTime = this.dateTime,
        content = this.content,
        sender = this.sender,
        isMy = this.sender.id == currentUserId,
        reactions = this.reactions
    )
}
