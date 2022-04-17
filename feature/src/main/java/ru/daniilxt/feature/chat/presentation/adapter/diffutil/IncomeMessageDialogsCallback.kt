package ru.daniilxt.feature.chat.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.Message

class IncomeMessageDialogsCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(
        oldItem: Message,
        newItem: Message
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Message,
        newItem: Message
    ): Boolean {
        return oldItem.dateTime == newItem.dateTime && oldItem.content == newItem.content &&
            oldItem.reactions == newItem.reactions && oldItem.sender == newItem.sender &&
            oldItem.isMy == newItem.isMy
    }
}
