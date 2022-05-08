package ru.daniilxt.feature.profile_user_achievements.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.UserAchievement

class UserAchievementCallback : DiffUtil.ItemCallback<UserAchievement>() {
    override fun areItemsTheSame(
        oldItem: UserAchievement,
        newItem: UserAchievement
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: UserAchievement,
        newItem: UserAchievement
    ): Boolean {
        return oldItem.title == newItem.title &&
            oldItem.achievementDescription == newItem.achievementDescription &&
            oldItem.date == newItem.date
    }
}
