package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.domain.model.Coordinates

data class CoordinatesResponse(
    @SerializedName("latitude")
    val latitude: Float,
    @SerializedName("longitude")
    val longitude: Float
)

fun CoordinatesResponse.toCoordinates() = Coordinates(
    latitude = latitude,
    longitude = longitude
)
