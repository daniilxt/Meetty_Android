package ru.daniilxt.feature.data.remote.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("city")
    val city: CityResponse,
    @SerializedName("address")
    val address: String,
    @SerializedName("coordinates")
    val coordinates: CoordinatesResponse
)