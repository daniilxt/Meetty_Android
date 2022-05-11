package ru.daniilxt.feature.main_screen.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.UserCard

class UserCardCallback : DiffUtil.ItemCallback<UserCard>() {
    override fun areItemsTheSame(
        oldItem: UserCard,
        newItem: UserCard
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserCard,
        newItem: UserCard
    ): Boolean {
        return oldItem.id == newItem.id && oldItem.user == newItem.user &&
            oldItem.userAdditionalInfo == newItem.userAdditionalInfo
    }
}
