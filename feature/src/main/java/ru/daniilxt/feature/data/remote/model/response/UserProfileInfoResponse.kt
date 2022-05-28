package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.UserCardInfo
import java.time.LocalDateTime

data class UserProfileInfoResponse(
    @SerializedName("userInfo")
    val userInfo: SimpleUserResponse,
    @SerializedName("userAdditionalInfo")
    val userAdditionalInfo: UserAdditionalInfoResponse,
    @SerializedName("userEducation")
    val userEducationInfo: EducationInstituteResponse,
    @SerializedName("lastActivity")
    val lastActivity: LocalDateTime?
)

fun UserProfileInfoResponse.toUserCardInfo() = UserCardInfo(
    userInfo = userInfo.toSimpleUser(),
    userAdditionalInfo = userAdditionalInfo.toUserAdditionalInfo(),
    userEducationInfo = userEducationInfo.toEducationInstitute(),
    lastActivity = lastActivity,
)
