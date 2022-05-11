package ru.daniilxt.feature.main_screen.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemUserCardBinding
import ru.daniilxt.feature.domain.model.UserCard
import ru.daniilxt.feature.main_screen.presentation.adapter.diffutil.UserCardCallback
import ru.daniilxt.feature.main_screen.presentation.adapter.view_holder.UserCardViewHolder

class UserCardAdapter(
    private val onItemClickListener: (data: UserCard) -> Unit,
) : RecyclerView.Adapter<UserCardViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserCardViewHolder =
        UserCardViewHolder(parent.viewBinding(ItemUserCardBinding::inflate))

    override fun onBindViewHolder(holder: UserCardViewHolder, position: Int) {
        holder.bind(
            differ.currentList[position],
            this.onItemClickListener,
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(data: List<UserCard>) {
        differ.submitList(data)
    }

    companion object {
        private val DIFF_CALLBACK = UserCardCallback()
    }
}