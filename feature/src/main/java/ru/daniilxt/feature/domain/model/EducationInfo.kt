package ru.daniilxt.feature.domain.model

data class EducationInfo(
    val id: Long,
    val name: String,
    val location: LocationInfo,
    val logoLink: String = ""
)
