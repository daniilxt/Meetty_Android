package ru.daniilxt.feature.main_screen_user_list.presentation.adapter.view_holder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ru.daniilxt.common.extensions.getAgeFromNumber
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.databinding.ItemUserCardBinding
import ru.daniilxt.feature.domain.model.UserCard
import ru.daniilxt.feature.files_helper.FilesHelper
import timber.log.Timber
import java.time.LocalDate

class UserCardViewHolder(
    private val binding: ItemUserCardBinding
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("NewApi")
    fun bind(data: UserCard, onItemClickListener: (data: UserCard) -> Unit) {
        with(binding) {
           val str = data.user.avatarBitmap?.allocationByteCount?.let { FilesHelper.getReadableFileSize(it.toLong()) }
           val str2 = data.user.avatarBitmap?.allocationByteCount
            Timber.i("COMPRESSED $str  ${data.user.id}  $str2")
            ivAvatar.load(data.user.avatarBitmap) {
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
                views[i].text = categories[i].interestName
            }
        }
        itemView.setDebounceClickListener {
            onItemClickListener(data)
        }
    }
}
