package ru.daniilxt.feature.chat.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.chat.presentation.adapter.diffutil.IncomeMessageDialogsCallback
import ru.daniilxt.feature.chat.presentation.adapter.view_holder.EmojiMessageViewHolder
import ru.daniilxt.feature.chat.presentation.adapter.view_holder.IncomeEmojiMessageViewHolder
import ru.daniilxt.feature.chat.presentation.adapter.view_holder.OutcomeEmojiMessageViewHolder
import ru.daniilxt.feature.databinding.ItemIncomeEmojiMessageBinding
import ru.daniilxt.feature.databinding.ItemOutcomeEmojiMessageBinding
import ru.daniilxt.feature.domain.model.Message
import ru.daniilxt.feature.domain.model.Reaction

class MessageAdapter(
    private val onEmojiClickListener: (messageId: Long, reaction: Reaction) -> Unit,
    private val onMessageLongCLickListener: (id: Long) -> Unit
) : RecyclerView.Adapter<EmojiMessageViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    override fun getItemViewType(position: Int) =
        if (differ.currentList[position].isMy) OUTCOME_MESSAGE else INCOME_MESSAGE

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmojiMessageViewHolder<*> = when (viewType) {
        INCOME_MESSAGE -> {
            IncomeEmojiMessageViewHolder(
                parent.viewBinding(
                    ItemIncomeEmojiMessageBinding::inflate
                )
            )
        }
        OUTCOME_MESSAGE -> {
            OutcomeEmojiMessageViewHolder(
                parent.viewBinding(
                    ItemOutcomeEmojiMessageBinding::inflate
                )
            )
        }
        else -> throw IllegalArgumentException("Invalid view type")
    }

    override fun onBindViewHolder(holder: EmojiMessageViewHolder<*>, position: Int) {
        holder.bind(
            differ.currentList[position],
            this.onEmojiClickListener,
            this.onMessageLongCLickListener
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(data: List<Message>) {
        differ.submitList(data)
    }

    companion object {
        private val DIFF_CALLBACK = IncomeMessageDialogsCallback()
        private const val INCOME_MESSAGE = 0
        private const val OUTCOME_MESSAGE = 1
    }
}
