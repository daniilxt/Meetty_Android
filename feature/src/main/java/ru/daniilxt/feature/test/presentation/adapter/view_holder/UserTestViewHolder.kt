package ru.daniilxt.feature.test.presentation.adapter.view_holder

import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.feature.databinding.ItemUserTestBinding
import ru.daniilxt.feature.domain.model.PhotoAttach

class UserTestViewHolder(
    private val binding: ItemUserTestBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: PhotoAttach) {
    }
}
