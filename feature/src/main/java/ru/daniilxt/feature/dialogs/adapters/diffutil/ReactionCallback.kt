package ru.daniilxt.feature.dialogs.adapters.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.Reaction

class ReactionCallback : DiffUtil.ItemCallback<Reaction>() {
    override fun areItemsTheSame(
        oldItem: Reaction,
        newItem: Reaction
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Reaction,
        newItem: Reaction
    ): Boolean {
        return oldItem.emojiText == newItem.emojiText && oldItem.usersId == newItem.usersId &&
            oldItem.count == newItem.count
    }
}
