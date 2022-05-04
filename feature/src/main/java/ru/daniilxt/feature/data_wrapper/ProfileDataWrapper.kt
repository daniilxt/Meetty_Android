package ru.daniilxt.feature.data_wrapper

import kotlinx.coroutines.flow.MutableStateFlow

class ProfileDataWrapper {
    private val _profileData: MutableStateFlow<ProfileData> =
        MutableStateFlow(ProfileData())

    fun setUserCredentials(userCredentials: UserCredentials) {
        _profileData.value = _profileData.value.copy(useCredentials = userCredentials)
    }

    fun getData() = _profileData.value
}

data class ProfileData(
    val useCredentials: UserCredentials? = null,
)

data class UserCredentials(
    val email: String,
    val password: String
)
