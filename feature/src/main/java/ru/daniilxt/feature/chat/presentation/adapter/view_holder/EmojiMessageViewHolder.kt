package ru.daniilxt.feature.chat.presentation.adapter.view_holder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.daniilxt.feature.domain.model.ChatMessage
import ru.daniilxt.feature.domain.model.Reaction

abstract class EmojiMessageViewHolder<T>(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    open fun bind(
        message: ChatMessage,
        onEmojiClickListener: (messageId: Long, reaction: Reaction) -> Unit,
        onMessageLongCLickListener: (messageId: Long) -> Unit
    ) {
    }
}
