package ru.daniilxt.feature.domain.model

import ru.daniilxt.feature.data.remote.model.body.UserEducationInfoBody
import java.io.File

data class UserEducationInfo(
    val city: City,
    val institute: EducationInstitute,
    val photos: List<File>
)

fun UserEducationInfo.toUserEducationInfoBody() = UserEducationInfoBody(
    instituteId = institute.id,
    photos = photos
)
