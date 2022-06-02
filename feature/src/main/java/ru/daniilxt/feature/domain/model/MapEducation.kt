package ru.daniilxt.feature.domain.model

data class MapEducation(
    val edu: EducationInfo,
    val users: List<MapSimpleUser>
)