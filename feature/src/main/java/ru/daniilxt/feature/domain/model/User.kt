package ru.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val avatarLink: String
) : Parcelable {
    fun getCapitalizedFullUserName(): String {
        return "${firstName.replaceFirstChar { it.uppercase() }} ${lastName.replaceFirstChar { it.uppercase() }} "
    }
}
