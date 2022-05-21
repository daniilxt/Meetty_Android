package ru.daniilxt.feature.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.daniilxt.feature.data_wrapper.EducationInstitute

data class EducationInstituteResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location: LocationResponse,
    @SerializedName("logoUri")
    val logoUri: String
)

fun EducationInstituteResponse.toEducationInstitute() = EducationInstitute(
    id = id,
    instituteName = name,
    city = location.city.toCity()
)