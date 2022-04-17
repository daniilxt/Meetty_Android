package ru.daniilxt.feature.chat.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.chat.presentation.adapter.diffutil.IncomeMessageDialogsCallback
import ru.daniilxt.feature.chat.presentation.adapter.view_holder.OutcomeEmojiMessageViewHolder
import ru.daniilxt.feature.databinding.ItemOutcomeEmojiMessageBinding
import ru.daniilxt.feature.domain.model.Message
import ru.daniilxt.feature.domain.model.Reaction

class MessageAdapter(
    private val onEmojiClickListener: (messageId: Long, reaction: Reaction) -> Unit,
    private val onMessageLongCLickListener: (id: Long) -> Unit
) : RecyclerView.Adapter<OutcomeEmojiMessageViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OutcomeEmojiMessageViewHolder =
        OutcomeEmojiMessageViewHolder(parent.viewBinding(ItemOutcomeEmojiMessageBinding::inflate))

    override fun onBindViewHolder(holder: OutcomeEmojiMessageViewHolder, position: Int) {
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
    }
}
