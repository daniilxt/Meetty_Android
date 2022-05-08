package ru.daniilxt.feature.profile_user_achievements.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemAchievementBinding
import ru.daniilxt.feature.domain.model.UserAchievement
import ru.daniilxt.feature.profile_user_achievements.presentation.adapter.diffutil.UserAchievementCallback
import ru.daniilxt.feature.profile_user_achievements.presentation.adapter.view_holder.UserAchievementViewHolder

class UserAchievementAdapter(
    private val onDeleteAchieveClickListener: (userAchieve: UserAchievement) -> Unit,
) : RecyclerView.Adapter<UserAchievementViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAchievementViewHolder =
        UserAchievementViewHolder(parent.viewBinding(ItemAchievementBinding::inflate))

    override fun onBindViewHolder(holder: UserAchievementViewHolder, position: Int) {
        holder.bind(
            differ.currentList[position],
            this.onDeleteAchieveClickListener,
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(data: List<UserAchievement>) {
        differ.submitList(data)
    }

    companion object {
        private val DIFF_CALLBACK = UserAchievementCallback()
    }
}
