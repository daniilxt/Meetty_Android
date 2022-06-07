package ru.daniilxt.feature.main_screen_map.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemEduUserCardBinding
import ru.daniilxt.feature.domain.model.MapSimpleUser
import ru.daniilxt.feature.main_screen_map.presentation.adapter.diffutil.EduUserCardCallback
import ru.daniilxt.feature.main_screen_map.presentation.adapter.view_holder.EduUserCardViewHolder

class EduUserCardAdapter(
    private val onItemClickListener: (data: MapSimpleUser) -> Unit,
) : RecyclerView.Adapter<EduUserCardViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EduUserCardViewHolder =
        EduUserCardViewHolder(parent.viewBinding(ItemEduUserCardBinding::inflate))

    override fun onBindViewHolder(holder: EduUserCardViewHolder, position: Int) {
        holder.bind(differ.currentList[position], this.onItemClickListener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(data: List<MapSimpleUser>) {
        differ.submitList(data)
    }

    companion object {
        private val DIFF_CALLBACK = EduUserCardCallback()
    }
}
