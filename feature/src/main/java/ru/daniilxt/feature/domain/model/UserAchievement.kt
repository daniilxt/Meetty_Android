package ru.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.daniilxt.common.extensions.toSendDateString
import ru.daniilxt.feature.data.remote.model.body.UserAchievementsBody
import java.time.LocalDate

@Parcelize
data class UserAchievement(
    val title: String,
    val achievementDescription: String,
    val date: LocalDate
) : Parcelable

fun UserAchievement.toUserAchievementBody() = UserAchievementsBody(
    title = title,
    achievementDescription = achievementDescription,
    date = date.toSendDateString()
)
