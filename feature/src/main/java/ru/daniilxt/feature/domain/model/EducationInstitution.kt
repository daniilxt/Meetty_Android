package ru.daniilxt.feature.domain.model

data class EducationInstitution(
    val id: Long,
    val name: String,
    val location: LocationInfo,
    val logoUri: String = ""
)
