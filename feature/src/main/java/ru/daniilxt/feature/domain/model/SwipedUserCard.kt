package ru.daniilxt.feature.domain.model

data class SwipedUserCard(
    val name: String,
    val photoUri: String,
    val professionalInterest: List<ProfessionalInterest>
)
