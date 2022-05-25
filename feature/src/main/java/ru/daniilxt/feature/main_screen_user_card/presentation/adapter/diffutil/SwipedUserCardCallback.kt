package ru.daniilxt.feature.main_screen_user_card.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.SwipedUserCard

class SwipedUserCardCallback : DiffUtil.ItemCallback<SwipedUserCard>() {
    override fun areItemsTheSame(
        oldItem: SwipedUserCard,
        newItem: SwipedUserCard
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: SwipedUserCard,
        newItem: SwipedUserCard
    ): Boolean {
        return oldItem.name == newItem.name && oldItem.photoUri == newItem.photoUri
    }
}
