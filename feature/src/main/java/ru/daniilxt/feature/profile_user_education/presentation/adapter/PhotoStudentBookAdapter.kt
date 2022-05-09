package ru.daniilxt.feature.profile_user_education.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemAttachPhotoBinding
import ru.daniilxt.feature.domain.model.PhotoAttach
import ru.daniilxt.feature.profile_user_education.presentation.adapter.diffutil.PhotoStudentBookCallback
import ru.daniilxt.feature.profile_user_education.presentation.adapter.view_holder.PhotoStudentBookViewHolder

class PhotoStudentBookAdapter(
    private val onDeletePhotoClickListener: (photoAttach: PhotoAttach) -> Unit,
    private val onAddPhotoClickListener: () -> Unit
) : RecyclerView.Adapter<PhotoStudentBookViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoStudentBookViewHolder =
        PhotoStudentBookViewHolder(parent.viewBinding(ItemAttachPhotoBinding::inflate))

    override fun onBindViewHolder(holder: PhotoStudentBookViewHolder, position: Int) {
        holder.bind(
            differ.currentList[position],
            this.onDeletePhotoClickListener,
            this.onAddPhotoClickListener
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(data: List<PhotoAttach>) {
        differ.submitList(data)
    }

    companion object {
        private val DIFF_CALLBACK = PhotoStudentBookCallback()
    }
}
