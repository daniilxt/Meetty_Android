package ru.daniilxt.feature.domain.model

import android.graphics.Bitmap

data class SimpleUserInfo(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val avatarBitmap: Bitmap?,
    val sex: String = ""
) {
    fun getFullUserName() = "$firstName $lastName"
}
