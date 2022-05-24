package ru.daniilxt.feature.domain.model

import ru.daniilxt.feature.data.remote.model.body.RegistrationInfoBody

data class ProfileData(
    val userCredentials: UserCredentials? = null,
    val userPersonalInfo: UserPersonalInfo? = null,
    val userEducationInfo: UserEducationInfo? = null,
    val professionalInterest: List<ProfessionalInterest> = emptyList(),
    val userAchievements: List<UserAchievement> = emptyList()
)

fun ProfileData.toRegistrationInfoBody() = RegistrationInfoBody(
    userCredentials = requireNotNull(userCredentials?.toUserCredentialsBody()),
    userPersonalInfo = requireNotNull(userPersonalInfo?.toUserPersonalInfoBody()),
    userEducationInfo = requireNotNull(userEducationInfo?.toUserEducationInfoBody()),
    professionalInterest = professionalInterest.map { it.toProfessionalInterestBody() },
    userAchievements = userAchievements.map { it.toUserAchievementBody() }
)
