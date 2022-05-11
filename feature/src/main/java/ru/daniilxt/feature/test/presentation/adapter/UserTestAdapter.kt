package ru.daniilxt.feature.test.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemUserTestBinding
import ru.daniilxt.feature.domain.model.PhotoAttach
import ru.daniilxt.feature.test.presentation.adapter.diffutil.UserTestCallback
import ru.daniilxt.feature.test.presentation.adapter.view_holder.UserTestViewHolder

class UserTestAdapter(
    private val onItemClickListener: (data: PhotoAttach) -> Unit,
) : RecyclerView.Adapter<UserTestViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserTestViewHolder =
        UserTestViewHolder(parent.viewBinding(ItemUserTestBinding::inflate))

    override fun onBindViewHolder(holder: UserTestViewHolder, position: Int) {
        holder.bind(
            differ.currentList[position],
            this.onItemClickListener,
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(data: List<PhotoAttach>) {
        differ.submitList(data)
    }

    companion object {
        private val DIFF_CALLBACK = UserTestCallback()
    }
}

