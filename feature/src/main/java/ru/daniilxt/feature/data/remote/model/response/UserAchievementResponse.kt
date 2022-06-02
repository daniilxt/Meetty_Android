package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.common.extensions.toLocalDate
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
    date = date?.toLocalDate() ?: LocalDate.of(1970, 1, 1)
)
