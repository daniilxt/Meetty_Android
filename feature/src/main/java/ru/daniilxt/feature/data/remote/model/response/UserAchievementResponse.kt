package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.common.extensions.parseLocalDate
import ru.daniilxt.feature.domain.model.UserAchievement
import java.time.LocalDate

data class UserAchievementResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val achievementDescription: String = "",
    @SerializedName("date")
    val date: String?
)

fun UserAchievementResponse.toUserAchievement() = UserAchievement(
    title = title,
    achievementDescription = achievementDescription,
    date = date?.parseLocalDate() ?: LocalDate.of(1970, 1, 1)
)
