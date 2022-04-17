package ru.daniilxt.feature.chat.presentation.adapter.view_holder

import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.widgets.EmojiView
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.ItemOutcomeEmojiMessageBinding
import ru.daniilxt.feature.domain.model.Message
import ru.daniilxt.feature.domain.model.Reaction
import timber.log.Timber

class OutcomeEmojiMessageViewHolder(val binding: ItemOutcomeEmojiMessageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        message: Message,
        onEmojiClickListener: (messageId: Long, reaction: Reaction) -> Unit,
        onMessageLongCLickListener: (messageId: Long) -> Unit
    ) {
        with(binding) {
            Timber.i("???? $message")
            tvName.text = message.sender.getCapitalizedFullUserName()
            tvMessage.text = message.content
            val hour = message.dateTime.toLocalTime().hour.toString()
            val min = message.dateTime.toLocalTime().minute.toString()
            val time =  "${hour}:${min}"
            Timber.i("CUSTOM $time")
            tvDate.text =time
            flexbox.removeAllViews()
            message.reactions.forEach { reaction ->
                val emojiView = EmojiView(binding.root.context)
                emojiView.reactionsCount = reaction.count.toInt()
                emojiView.text = reaction.emojiText
                emojiView.setBackgroundResource(R.drawable.bg_custom_text_view)
                emojiView.setOnClickListener { v ->
                    v.isSelected = !v.isSelected
                    onEmojiClickListener(message.id, reaction)
                }
                flexbox.addView(emojiView)
            }
        }
        itemView.setOnLongClickListener {
            onMessageLongCLickListener(message.id)
            true
        }
    }
}
