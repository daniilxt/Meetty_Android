package ru.daniilxt.feature.domain.model

data class LocationInfo(
    val city: String,
    val address: String,
    val coordinates: CoordinatesDto
)
