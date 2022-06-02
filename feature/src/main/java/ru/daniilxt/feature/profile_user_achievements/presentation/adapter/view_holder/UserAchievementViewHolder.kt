package ru.daniilxt.feature.profile_user_achievements.presentation.adapter.view_holder

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.databinding.ItemAchievementBinding
import ru.daniilxt.feature.domain.model.UserAchievement
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")

class UserAchievementViewHolder(
    private val binding: ItemAchievementBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        data: UserAchievement,
        onDeleteAchieveClickListener: (userAchieve: UserAchievement) -> Unit,
        isDeletable: Boolean,
    ) {
        with(binding) {
            tvTitle.text = data.title
            tvDate.text = data.date.format(format)
            tvDescription.text = data.achievementDescription
            ivDelete.isVisible = isDeletable

            ivDelete.setDebounceClickListener {
                onDeleteAchieveClickListener(data)
            }
        }
    }

    companion object {
        val format: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    }
}
