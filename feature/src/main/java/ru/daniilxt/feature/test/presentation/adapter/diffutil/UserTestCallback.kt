package ru.daniilxt.feature.test.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.PhotoAttach

class UserTestCallback : DiffUtil.ItemCallback<PhotoAttach>() {
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
