package ru.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Parcelize
data class Message(
    val id: Long,
    val dateTime: LocalDateTime,
    val content: String,
    val reactions: List<Reaction> = emptyList(),
    val sender: User,
    val isMy: Boolean = false
) : Parcelable
