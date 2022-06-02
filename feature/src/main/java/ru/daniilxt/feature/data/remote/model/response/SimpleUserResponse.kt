package ru.daniilxt.feature.data.remote.model.response

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.annotations.SerializedName
import okio.ByteString.Companion.decodeBase64
import ru.daniilxt.common.BuildConfig
import ru.daniilxt.feature.domain.model.SimpleUserInfo

data class SimpleUserResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("avatarLink")
    val avatarLink: String,
    @SerializedName("sex")
    val sex: String
)

@SuppressLint("NewApi")
fun SimpleUserResponse.toSimpleUser() = SimpleUserInfo(
    id = id,
    firstName = firstName,
    lastName = lastName,
    avatarLink = BuildConfig.ENDPOINT + avatarLink,
    sex = sex,
)

fun String.getBitmapByStringByteArray(): Bitmap {
    val byteArray = this.decodeBase64()!!.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}
