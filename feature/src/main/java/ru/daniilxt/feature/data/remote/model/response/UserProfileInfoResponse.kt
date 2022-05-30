package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.UserProfileInfo
import java.time.LocalDateTime

data class UserProfileInfoResponse(
    @SerializedName("userInfo")
    val userInfo: SimpleUserResponse,
    @SerializedName("userAdditionalInfo")
    val userAdditionalInfo: UserAdditionalInfoResponse,
    @SerializedName("userEducation")
    val userEducationInfo: EducationInstituteResponse,
    @SerializedName("userAchievements")
    val userAchievements: List<UserAchievementResponse>? = null,
    @SerializedName("lastActivity")
    val lastActivity: LocalDateTime?
)

fun UserProfileInfoResponse.toUserProfileInfo() = UserProfileInfo(
    userInfo = userInfo.toSimpleUser(),
    userAdditionalInfo = userAdditionalInfo.toUserAdditionalInfo(),
    userEducationInfo = userEducationInfo.toEducationInfo(),
    userAchievements = userAchievements?.map { it.toUserAchievement() } ?: emptyList(),
    lastActivity = lastActivity
)
