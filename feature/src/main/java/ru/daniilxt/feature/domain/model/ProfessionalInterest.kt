package ru.daniilxt.feature.domain.model

import ru.daniilxt.feature.data.remote.model.body.ProfessionalInterestBody

data class ProfessionalInterest(
    val id: Long,
    val interestName: String
)

fun ProfessionalInterest.toProfessionalInterestBody() = ProfessionalInterestBody(
    id = id,
    interestName = interestName
)