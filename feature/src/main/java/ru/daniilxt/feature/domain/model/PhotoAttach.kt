package ru.daniilxt.feature.domain.model

import android.net.Uri

data class PhotoAttach(
    val photoUri: Uri,
    val attachType: AttachType
)

fun Uri.toPhotoAttach(): PhotoAttach {
    return PhotoAttach(this, AttachType.PhotoUri)
}
