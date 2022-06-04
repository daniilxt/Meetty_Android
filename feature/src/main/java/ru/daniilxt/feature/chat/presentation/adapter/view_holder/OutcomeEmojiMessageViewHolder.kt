package ru.daniilxt.feature.chat.presentation.adapter.view_holder

import android.annotation.SuppressLint
import ru.daniilxt.common.extensions.toLocalTimeFormat
import ru.daniilxt.common.widgets.EmojiView
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.ItemOutcomeEmojiMessageBinding
import ru.daniilxt.feature.domain.model.Message
import ru.daniilxt.feature.domain.model.Reaction

@SuppressLint("NewApi")

class OutcomeEmojiMessageViewHolder(val binding: ItemOutcomeEmojiMessageBinding) :
    EmojiMessageViewHolder<OutcomeEmojiMessageViewHolder>(binding) {
    override fun bind(
        message: Message,
        onEmojiClickListener: (messageId: Long, reaction: Reaction) -> Unit,
        onMessageLongCLickListener: (messageId: Long) -> Unit
    ) {
        with(binding) {
            tvMessage.text = message.content
            val time = message.dateTime.toLocalTime()
            tvDate.text = time.toLocalTimeFormat()
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
