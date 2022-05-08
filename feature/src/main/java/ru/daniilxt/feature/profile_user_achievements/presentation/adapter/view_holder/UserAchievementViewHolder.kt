package ru.daniilxt.feature.profile_user_achievements.presentation.adapter.view_holder

import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.databinding.ItemAchievementBinding
import ru.daniilxt.feature.domain.model.UserAchievement

class UserAchievementViewHolder(
    private val binding: ItemAchievementBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        data: UserAchievement,
        onDeleteAchieveClickListener: (userAchieve: UserAchievement) -> Unit,
    ) {
        with(binding) {
            tvTitle.text = data.title
            tvDate.text = data.date.toString()
            tvDescription.text = data.achievementDescription

            ivDelete.setDebounceClickListener {
                onDeleteAchieveClickListener(data)
            }
        }
    }
}
