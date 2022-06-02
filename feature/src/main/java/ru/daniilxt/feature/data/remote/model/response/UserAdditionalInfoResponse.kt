package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.common.extensions.toLocalDate
import ru.daniilxt.feature.domain.model.UserAdditionalInfo

data class UserAdditionalInfoResponse(
    @SerializedName("professionalInterests")
    val professionalInterests: List<ProfessionalInterestResponse>,
    @SerializedName("userBirthday")
    val userBirthday: String,
    @SerializedName("userPhone")
    val userPhone: String?
)

fun UserAdditionalInfoResponse.toUserAdditionalInfo() = UserAdditionalInfo(
    id = -1,
    birthDay = userBirthday.toLocalDate(),
    categories = professionalInterests.map { it.toProfessionalInterest() },
    userPhone = if (userPhone.isNullOrEmpty()) "" else userPhone
)
