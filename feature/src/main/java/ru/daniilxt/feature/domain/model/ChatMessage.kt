package ru.daniilxt.feature.domain.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@SuppressLint("NewApi")
@Parcelize
data class ChatMessage(
    val id: Long = -1L,
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val content: String = "",
    val reactions: List<Reaction> = emptyList(),
    val sender: User,
    val isMy: Boolean = false
) : Parcelable
