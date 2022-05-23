package ru.daniilxt.feature.data.remote.model.body

import com.google.gson.annotations.SerializedName

data class ProfessionalInterestBody(
    @SerializedName("id")
    val id: Long,
    @SerializedName("interestName")
    val interestName: String
)