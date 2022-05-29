package ru.daniilxt.feature.domain.model

data class SimpleUserInfo(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val avatarUri: String,
    val sex: String = ""
) {
    fun getFullUserName() = "$firstName $lastName"
}
