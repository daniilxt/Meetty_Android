package ru.daniilxt.feature.welcome_screen_description.presentation.adapter.view_holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.daniilxt.feature.databinding.ItemAppFeatureBinding
import ru.daniilxt.feature.domain.model.FeatureDescription

class AppFeatureDescriptionViewHolder(
    private val binding: ItemAppFeatureBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: FeatureDescription) {
        binding.tvTitle.text = data.title
        binding.tvDescription.text = data.description
        binding.tvTitle.text = data.title
        binding.ivIcon.load(data.photo)
    }
}