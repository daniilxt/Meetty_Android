package ru.daniilxt.feature.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Message(
    val id: Long,
    val date: LocalDate,
    val time: LocalTime,
    val content: String,
    val reactions: List<Reaction> = emptyList(),
    val sender: User
)