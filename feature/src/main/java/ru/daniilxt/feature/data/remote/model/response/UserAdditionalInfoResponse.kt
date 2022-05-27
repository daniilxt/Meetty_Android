package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.UserAdditionalInfo
import java.time.LocalDate

data class UserAdditionalInfoResponse(
    @SerializedName("professionalInterests")
    val professionalInterests: List<ProfessionalInterestResponse>,
    @SerializedName("userBirthday")
    val userBirthday: LocalDate
)

fun UserAdditionalInfoResponse.toUserAdditionalInfo() = UserAdditionalInfo(
    id = -1,
    birthDay = userBirthday,
    categories = professionalInterests.map { it.toProfessionalInterest() }
)
