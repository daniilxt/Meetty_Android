package ru.daniilxt.feature.chat.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.ChatMessage

class IncomeMessageDialogsCallback : DiffUtil.ItemCallback<ChatMessage>() {
    override fun areItemsTheSame(
        oldItem: ChatMessage,
        newItem: ChatMessage
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ChatMessage,
        newItem: ChatMessage
    ): Boolean {
        return oldItem.dateTime == newItem.dateTime && oldItem.content == newItem.content &&
            oldItem.reactions == newItem.reactions && oldItem.sender == newItem.sender &&
            oldItem.isMy == newItem.isMy
    }
}
