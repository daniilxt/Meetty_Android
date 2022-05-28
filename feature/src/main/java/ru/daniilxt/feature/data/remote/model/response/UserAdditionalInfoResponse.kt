package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.common.extensions.parseLocalDate
import ru.daniilxt.feature.domain.model.UserAdditionalInfo

data class UserAdditionalInfoResponse(
    @SerializedName("professionalInterests")
    val professionalInterests: List<ProfessionalInterestResponse>,
    @SerializedName("userBirthday")
    val userBirthday: String
)

fun UserAdditionalInfoResponse.toUserAdditionalInfo() = UserAdditionalInfo(
    id = -1,
    birthDay = userBirthday.parseLocalDate(),
    categories = professionalInterests.map { it.toProfessionalInterest() }
)
