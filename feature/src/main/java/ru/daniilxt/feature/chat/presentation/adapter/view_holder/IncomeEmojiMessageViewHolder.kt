package ru.daniilxt.feature.chat.presentation.adapter.view_holder

import coil.load
import coil.transform.CircleCropTransformation
import ru.daniilxt.common.widgets.EmojiView
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.ItemIncomeEmojiMessageBinding
import ru.daniilxt.feature.domain.model.ChatMessage
import ru.daniilxt.feature.domain.model.Reaction
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider

class IncomeEmojiMessageViewHolder(val binding: ItemIncomeEmojiMessageBinding) :
    EmojiMessageViewHolder<IncomeEmojiMessageViewHolder>(binding) {
    override fun bind(
        message: ChatMessage,
        onEmojiClickListener: (messageId: Long, reaction: Reaction) -> Unit,
        onMessageLongCLickListener: (messageId: Long) -> Unit
    ) {
        with(binding) {
            tvName.text = message.sender.getCapitalizedFullUserName()
            tvMessage.text = message.content
            val time = message.dateTime.toLocalTime()
            tvDate.text = "${time.hour}:${time.minute}:${time.second}"
            ivPhoto.load(message.sender.avatarLink) {
                transformations(CircleCropTransformation())
            }
            flexbox.removeAllViews()
            message.reactions.forEach { reaction ->
                val emojiView = EmojiView(binding.root.context)
                emojiView.reactionsCount = reaction.count.toInt()
                emojiView.text = reaction.emojiText
                emojiView.setBackgroundResource(R.drawable.bg_custom_text_view)

                if (UserDialogsProvider.myUser.id in reaction.usersId) {
                    emojiView.isSelected = true
                }

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
