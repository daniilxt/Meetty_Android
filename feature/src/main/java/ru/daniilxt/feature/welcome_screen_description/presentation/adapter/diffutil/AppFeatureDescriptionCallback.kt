package ru.daniilxt.feature.welcome_screen_description.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.FeatureDescription

class AppFeatureDescriptionCallback : DiffUtil.ItemCallback<FeatureDescription>() {
    override fun areItemsTheSame(
        oldItem: FeatureDescription,
        newItem: FeatureDescription
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: FeatureDescription,
        newItem: FeatureDescription
    ): Boolean {
        return oldItem.description == newItem.description && oldItem.title == newItem.title
    }
}
