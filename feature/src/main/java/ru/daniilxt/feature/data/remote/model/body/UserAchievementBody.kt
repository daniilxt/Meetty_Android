package ru.daniilxt.feature.data.remote.model.body

import com.google.gson.annotations.SerializedName

data class UserAchievementBody(
    @SerializedName("title")
    val title: String,
    @SerializedName("achievementDescription")
    val achievementDescription: String,
    @SerializedName("date")
    val date: String
)
