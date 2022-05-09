package ru.daniilxt.feature.profile_user_education.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.PhotoAttach

class PhotoStudentBookCallback : DiffUtil.ItemCallback<PhotoAttach>() {
    override fun areItemsTheSame(
        oldItem: PhotoAttach,
        newItem: PhotoAttach
    ): Boolean {
        return oldItem.photoUri == newItem.photoUri
    }

    override fun areContentsTheSame(
        oldItem: PhotoAttach,
        newItem: PhotoAttach
    ): Boolean {
        return oldItem.photoUri == newItem.photoUri && oldItem.attachType == newItem.attachType
    }
}
