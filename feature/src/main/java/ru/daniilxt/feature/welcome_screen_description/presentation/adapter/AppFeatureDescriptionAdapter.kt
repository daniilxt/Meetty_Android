package ru.daniilxt.feature.welcome_screen_description.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemAppFeatureBinding
import ru.daniilxt.feature.domain.model.FeatureDescription
import ru.daniilxt.feature.welcome_screen_description.presentation.adapter.diffutil.AppFeatureDescriptionCallback
import ru.daniilxt.feature.welcome_screen_description.presentation.adapter.view_holder.AppFeatureDescriptionViewHolder

class AppFeatureDescriptionAdapter : RecyclerView.Adapter<AppFeatureDescriptionViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppFeatureDescriptionViewHolder =
        AppFeatureDescriptionViewHolder(parent.viewBinding(ItemAppFeatureBinding::inflate))

    override fun onBindViewHolder(holder: AppFeatureDescriptionViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(data: List<FeatureDescription>) {
        differ.submitList(data)
    }

    companion object {
        private val DIFF_CALLBACK = AppFeatureDescriptionCallback()
    }
}
