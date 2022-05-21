package ru.daniilxt.feature.domain.model

data class LocationInfoDto(
    val city: String,
    val address: String,
    val coordinates: CoordinatesDto
)