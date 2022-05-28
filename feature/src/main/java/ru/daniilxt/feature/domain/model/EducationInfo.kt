package ru.daniilxt.feature.domain.model

data class EducationInfo(
    val id: Long,
    val name: String,
    val location: LocationInfo,
    val logoLink: String = ""
) {
}

fun EducationInfo.toEducationInfo(): EducationInstitute = EducationInstitute(
    id = id,
    instituteName = name,
    city = City(0, location.city)
)