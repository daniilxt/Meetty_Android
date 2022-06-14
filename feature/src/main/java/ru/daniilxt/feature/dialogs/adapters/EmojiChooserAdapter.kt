package ru.daniilxt.feature.dialogs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.feature.databinding.ItemEmojiBinding
import ru.daniilxt.feature.dialogs.adapters.diffutil.ReactionCallback
import ru.daniilxt.feature.dialogs.adapters.view_holder.ReactionViewHolder
import ru.daniilxt.feature.domain.model.Reaction

class EmojiChooserAdapter(
    private val onEmojiClickListener: (emoji: Reaction) -> Unit,
) : RecyclerView.Adapter<ReactionViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReactionViewHolder {
        val binding =
            ItemEmojiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReactionViewHolder, position: Int) {
        with(holder.binding) {
            val emoji = differ.currentList[position]
            itemEmoji.text = emoji.emojiText
            holder.itemView.setOnClickListener {
                onEmojiClickListener(emoji)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(emojiList: List<Reaction>) {
        differ.submitList(emojiList)
    }

    companion object {
        private val DIFF_CALLBACK = ReactionCallback()
    }
}
