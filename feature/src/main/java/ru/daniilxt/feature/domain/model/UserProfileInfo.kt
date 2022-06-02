package ru.daniilxt.feature.domain.model

import java.time.LocalDateTime

data class UserProfileInfo(
    val userInfo: SimpleUserInfo,
    val userAdditionalInfo: UserAdditionalInfo,
    val userEducationInfo: EducationInfo,
    val userAchievements: List<UserAchievement> = emptyList(),
    val lastActivity: LocalDateTime?
)

fun UserProfileInfo.toSwipesUserCard() = SwipedUserCard(
    userInfo = userInfo,
    userAdditionalInfo = userAdditionalInfo,
    userEducationInfo = userEducationInfo.toEducationInfo()
)

fun UserProfileInfo.toUserCard() = UserCard(
    id = userInfo.id,
    user = userInfo,
    userAdditionalInfo = userAdditionalInfo,
)

fun UserProfileInfo.toMapSimpleUser() = MapSimpleUser(
    userInfo = userInfo,
    userAdditionalInfo = userAdditionalInfo
)