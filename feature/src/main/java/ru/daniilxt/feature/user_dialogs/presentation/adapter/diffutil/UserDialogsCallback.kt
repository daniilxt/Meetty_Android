package ru.daniilxt.feature.user_dialogs.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.UserDialog

class UserDialogsCallback : DiffUtil.ItemCallback<UserDialog>() {
    override fun areItemsTheSame(
        oldItem: UserDialog,
        newItem: UserDialog
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserDialog,
        newItem: UserDialog
    ): Boolean {
        return oldItem.lastMessage == newItem.lastMessage && oldItem.firstUser == newItem.firstUser
                && oldItem.secondUser == newItem.secondUser
    }
}