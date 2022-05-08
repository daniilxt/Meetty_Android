package ru.daniilxt.feature.domain.model

import java.time.LocalDate

data class UserAchievement(
    val title: String,
    val achievementDescription: String,
    val date: LocalDate
)
