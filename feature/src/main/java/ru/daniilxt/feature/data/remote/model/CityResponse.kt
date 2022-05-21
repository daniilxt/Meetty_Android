package ru.daniilxt.feature.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.data_wrapper.City

data class CityResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)

fun CityResponse.toCity() = City(
    id = id,
    cityName = name
)