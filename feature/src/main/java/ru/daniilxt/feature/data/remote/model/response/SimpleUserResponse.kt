package ru.daniilxt.feature.data.remote.model.response

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64.DEFAULT
import com.google.gson.annotations.SerializedName
import okio.ByteString.Companion.decodeBase64
import ru.daniilxt.feature.domain.model.SimpleUserInfo
import timber.log.Timber
import java.lang.Byte.decode

data class SimpleUserResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("avatarFile")
    val avatarFile: String,
    @SerializedName("sex")
    val sex: String
)

@SuppressLint("NewApi")
fun SimpleUserResponse.toSimpleUser() = SimpleUserInfo(
    id = id,
    firstName = firstName,
    lastName = lastName,
    avatarBitmap = if (!avatarFile.isNullOrEmpty()) avatarFile.getBitmapByStringByteArray() else null,
    sex = sex,
)

fun String.getBitmapByStringByteArray(): Bitmap {
    val byteArray = this.decodeBase64()!!.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}