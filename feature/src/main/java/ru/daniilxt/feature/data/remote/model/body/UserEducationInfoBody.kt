package ru.daniilxt.feature.data.remote.model.body

import com.google.gson.annotations.SerializedName
import java.io.File

data class UserEducationInfoBody(
    @SerializedName("instituteId")
    val instituteId: Long,
    @SerializedName("photos")
    val photos: List<File>
)
