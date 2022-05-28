package ru.daniilxt.feature.domain.model

import java.time.LocalDateTime

data class UserCardInfo(
    val userInfo: SimpleUserInfo,
    val userAdditionalInfo: UserAdditionalInfo,
    val userEducationInfo: EducationInstitute,
    val lastActivity: LocalDateTime?
)

fun UserCardInfo.toSwipesUserCard() = SwipedUserCard(
    userInfo = userInfo,
    userAdditionalInfo = userAdditionalInfo,
    userEducationInfo = userEducationInfo
)

fun UserCardInfo.toUserCard() = UserCard(
    id = userInfo.id,
    user = userInfo,
    userAdditionalInfo = userAdditionalInfo,
)
