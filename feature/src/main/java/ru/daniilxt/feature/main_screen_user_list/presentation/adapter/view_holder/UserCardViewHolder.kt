package ru.daniilxt.feature.main_screen_user_list.presentation.adapter.view_holder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ru.daniilxt.common.extensions.getAgeFromNumber
import ru.daniilxt.feature.databinding.ItemUserCardBinding
import ru.daniilxt.feature.domain.model.UserCard
import java.time.LocalDate

class UserCardViewHolder(
    private val binding: ItemUserCardBinding
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("NewApi")
    fun bind(data: UserCard, onItemClickListener: (data: UserCard) -> Unit) {
        with(binding) {
            ivAvatar.load(data.user.avatarUri) {
                transformations(CircleCropTransformation())
            }
            tvFirstName.text = data.user.firstName
            tvLastName.text = data.user.lastName
            tvUserYear.text =
                "".getAgeFromNumber((LocalDate.now().year - data.userAdditionalInfo.birthDay.year))
            val categories = data.userAdditionalInfo.categories
            val count = when (categories.size) {
                1 -> 1
                2 -> 2
                else -> 3
            }
            val views =
                listOf(binding.tvCategory, binding.tvCategory2, binding.tvCategory3)
            for (i in 0 until count) {
                views[i].text = categories[i].name
            }
        }
    }
}
