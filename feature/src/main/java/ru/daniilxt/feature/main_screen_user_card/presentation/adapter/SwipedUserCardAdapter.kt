package ru.daniilxt.feature.main_screen_user_card.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemSwipedUserCardBinding
import ru.daniilxt.feature.domain.model.SwipedUserCard
import ru.daniilxt.feature.main_screen_user_card.presentation.adapter.diffutil.SwipedUserCardCallback
import ru.daniilxt.feature.main_screen_user_card.presentation.adapter.view_holder.SwipedUserCardViewHolder

class SwipedUserCardAdapter() : RecyclerView.Adapter<SwipedUserCardViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SwipedUserCardViewHolder =
        SwipedUserCardViewHolder(parent.viewBinding(ItemSwipedUserCardBinding::inflate))

    override fun onBindViewHolder(holder: SwipedUserCardViewHolder, position: Int) {
        holder.bind(
            differ.currentList[position]
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(data: List<SwipedUserCard>) {
        differ.submitList(data)
    }

    companion object {
        private val DIFF_CALLBACK = SwipedUserCardCallback()
    }
}
