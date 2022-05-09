package ru.daniilxt.feature.profile_user_education.presentation.adapter.view_holder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.ItemAttachPhotoBinding
import ru.daniilxt.feature.domain.model.AttachType
import ru.daniilxt.feature.domain.model.PhotoAttach

class PhotoStudentBookViewHolder(
    private val binding: ItemAttachPhotoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        data: PhotoAttach,
        onDeletePhotoClickListener: (photoAttach: PhotoAttach) -> Unit,
        onAddPhotoClickListener: () -> Unit
    ) {
        with(binding) {
            itemView.setOnClickListener(null)
            itemAttachPhotoIbDelete.setOnClickListener(null)
            when (data.attachType) {
                is AttachType.PhotoUri -> {
                    itemAttachPhotoIv.visibility = View.VISIBLE
                    itemView.isClickable = false
                    itemAttachPhotoIv.load(data.photoUri)
                    itemAttachPhotoIbDelete.visibility = View.VISIBLE
                    itemAttachPhotoIvAdd.visibility = View.GONE
                    itemAttachPhotoIbDelete.setDebounceClickListener {
                        onDeletePhotoClickListener(data)
                    }
                }
                is AttachType.AddPhoto -> {
                    itemView.setBackgroundResource(R.color.background_third)
                    itemAttachPhotoIv.visibility = View.GONE
                    itemView.isClickable = true
                    itemAttachPhotoIvWrapper.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_dash_rectangle_100
                    )
                    itemAttachPhotoIbDelete.visibility = View.GONE
                    itemAttachPhotoIvAdd.visibility = View.VISIBLE
                    itemView.setDebounceClickListener {
                        onAddPhotoClickListener.invoke()
                    }
                }
            }
        }
    }
}
