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
    val sex: String,
    @SerializedName("profilePicture")
    val profilePicture: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPersonalInfoBody

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (birthDay != other.birthDay) return false
        if (phoneNumber != other.phoneNumber) return false
        if (sex != other.sex) return false
        if (!profilePicture.contentEquals(other.profilePicture)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + birthDay.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + sex.hashCode()
        result = 31 * result + profilePicture.contentHashCode()
        return result
    }
}
