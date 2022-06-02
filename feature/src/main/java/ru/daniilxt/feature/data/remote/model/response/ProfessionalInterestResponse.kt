package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.ProfessionalInterest

data class ProfessionalInterestResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("interest")
    val interest: String
)

fun ProfessionalInterestResponse.toProfessionalInterest() = ProfessionalInterest(
    id = id,
    interestName = interest
)
