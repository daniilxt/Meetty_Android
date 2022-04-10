package ru.daniilxt.feature.domain.model

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val avatarLink: String
) {
    fun getCapitalizedFullUserName(): String {
        return "${firstName.replaceFirstChar { it.uppercase() }} ${lastName.replaceFirstChar { it.uppercase() }} "
    }
}
