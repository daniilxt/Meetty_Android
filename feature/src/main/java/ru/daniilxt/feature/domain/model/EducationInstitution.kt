package ru.daniilxt.feature.domain.model

data class EducationInstitution(
    val id: Long,
    val name: String,
    val location: LocationInfoDto,
    val logoUri: String = ""
)