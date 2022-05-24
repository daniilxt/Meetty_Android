package ru.daniilxt.feature.data.remote.model.body

import com.google.gson.annotations.SerializedName

data class UserPersonalInfoBody(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("birthDay")
    val birthDay: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("sex")
    val sex: String
)
