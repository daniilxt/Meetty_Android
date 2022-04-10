package ru.daniilxt.feature.domain.model

data class Reaction(
    val id: Long,
    val emojiText: String,
    val usersId: List<Long>
)