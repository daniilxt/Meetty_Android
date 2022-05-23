package ru.daniilxt.feature.data.remote.model.body

import com.google.gson.annotations.SerializedName

data class RegistrationInfoBody(
    @SerializedName("userCredentials")
    val userCredentials: UserCredentialsBody,
    @SerializedName("userPersonalInfo")
    val userPersonalInfo: UserPersonalInfoBody,
    @SerializedName("userEducationInfo")
    val userEducationInfo: UserEducationInfoBody,
    @SerializedName("professionalInterest")
    val professionalInterest: List<ProfessionalInterestBody>,
    @SerializedName("userAchievements")
    val userAchievements: List<UserAchievementsBody>
)