package ru.daniilxt.feature.chat.presentation

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.daniilxt.feature.domain.model.Message
import ru.daniilxt.feature.domain.model.Reaction
import ru.daniilxt.feature.domain.model.User
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider
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

fun ChatSocketMessage.toMessage(): Message {
    return Message(
        id = this.id,
        dateTime = this.dateTime,
        content = this.content,
        sender = this.sender,
        isMy = this.sender == UserDialogsProvider.myUser,
        reactions = this.reactions
    )
}
