package ru.daniilxt.feature.domain.model

import java.time.LocalDate

data class UserAdditionalInfo(
    val id: Long,
    val birthDay: LocalDate,
    val categories: List<ProfessionalInterest>
)
