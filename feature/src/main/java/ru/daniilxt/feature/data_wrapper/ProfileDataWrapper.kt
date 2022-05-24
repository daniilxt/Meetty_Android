package ru.daniilxt.feature.data_wrapper

import kotlinx.coroutines.flow.MutableStateFlow
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import ru.daniilxt.feature.domain.model.ProfileData
import ru.daniilxt.feature.domain.model.UserAchievement
import ru.daniilxt.feature.domain.model.UserCredentials
import ru.daniilxt.feature.domain.model.UserEducationInfo
import ru.daniilxt.feature.domain.model.UserPersonalInfo

class ProfileDataWrapper {
    private val _profileData: MutableStateFlow<ProfileData> =
        MutableStateFlow(ProfileData())

    fun setUserCredentials(userCredentials: UserCredentials) {
        _profileData.value = _profileData.value.copy(userCredentials = userCredentials)
    }

    fun setUserPersonalInfo(userPersonalInfo: UserPersonalInfo) {
        _profileData.value = _profileData.value.copy(userPersonalInfo = userPersonalInfo)
    }

    fun setUserEducationInfo(userEducationInfo: UserEducationInfo) {
        _profileData.value = _profileData.value.copy(userEducationInfo = userEducationInfo)
    }

    fun setProfessionalInterestsData(professionalInterest: List<ProfessionalInterest>) {
        _profileData.value = _profileData.value.copy(professionalInterest = professionalInterest)
    }

    fun setUserAchievements(userAchievements: List<UserAchievement>) {
        _profileData.value = _profileData.value.copy(userAchievements = userAchievements)
    }

    fun getData() = _profileData.value
}
