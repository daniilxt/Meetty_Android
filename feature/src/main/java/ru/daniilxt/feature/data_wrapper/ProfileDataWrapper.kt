package ru.daniilxt.feature.data_wrapper

import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.time.LocalDate

class ProfileDataWrapper {
    private val _profileData: MutableStateFlow<ProfileData> =
        MutableStateFlow(ProfileData())

    fun setUserCredentials(userCredentials: UserCredentials) {
        _profileData.value = _profileData.value.copy(useCredentials = userCredentials)
    }

    fun setUserPersonalInfo(userPersonalInfo: UserPersonalInfo) {
        _profileData.value = _profileData.value.copy(userPersonalInfo = userPersonalInfo)
    }

    fun setUserEducationInfo(userEducationInfo: UserEducationInfo) {
        _profileData.value = _profileData.value.copy(userEducationInfo = userEducationInfo)
    }

    fun getData() = _profileData.value
}

data class ProfileData(
    val useCredentials: UserCredentials? = null,
    val userPersonalInfo: UserPersonalInfo? = null,
    val userEducationInfo: UserEducationInfo? = null
)

data class UserCredentials(
    val email: String,
    val password: String
)

data class UserPersonalInfo(
    val firstName: String,
    val lastName: String,
    val birthDay: LocalDate,
    val phoneNumber: String,
    val sex: String
)

data class UserEducationInfo(
    val city: City,
    val institute: EducationInstitute,
    val photos: List<File>
)

data class City(
    val id: Long,
    val cityName: String
)

data class EducationInstitute(
    val id: Long,
    val instituteName: String,
    val city: City
)
