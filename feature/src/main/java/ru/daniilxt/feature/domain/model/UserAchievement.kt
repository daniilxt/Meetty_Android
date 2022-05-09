package ru.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class UserAchievement(
    val title: String,
    val achievementDescription: String,
    val date: LocalDate
) : Parcelable
