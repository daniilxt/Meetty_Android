package ru.daniilxt.feature.domain.model

import android.annotation.SuppressLint
import ru.daniilxt.common.extensions.toSendDateString
import ru.daniilxt.feature.data.remote.model.body.UserPersonalInfoBody
import java.time.LocalDate

data class UserPersonalInfo(
    val firstName: String,
    val lastName: String,
    val birthDay: LocalDate,
    val phoneNumber: String,
    val sex: String
)

@SuppressLint("NewApi")
fun UserPersonalInfo.toUserPersonalInfoBody() = UserPersonalInfoBody(
    firstName = firstName,
    lastName = lastName,
    birthDay = birthDay.toSendDateString(),
    phoneNumber = phoneNumber,
    sex = sex
)