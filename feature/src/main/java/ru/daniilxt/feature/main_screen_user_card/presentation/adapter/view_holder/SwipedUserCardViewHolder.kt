package ru.daniilxt.feature.main_screen_user_card.presentation.adapter.view_holder

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import ru.daniilxt.common.extensions.getAgeFromNumber
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.ItemSwipedUserCardBinding
import ru.daniilxt.feature.domain.model.SwipedUserCard
import java.time.LocalDate

class SwipedUserCardViewHolder(
    private val binding: ItemSwipedUserCardBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: SwipedUserCard) {
        with(binding) {
            ivAvatar.load(data.photoUri) {
                transformations(CircleCropTransformation())
            }
            ivAvatarBlured.load(data.photoUri) {
                transformations(
                    BlurTransformation(
                        binding.root.context, 25F, 1F
                    )
                )
            }
            tvUserYear.text =
                "".getAgeFromNumber((LocalDate.now().year - data.userAdditionalInfo.birthDay.year))
            tvUsername.text = data.name
            chipGroupInterests.removeAllViews()
            for (item in data.userAdditionalInfo.categories) {
                val layoutInflater = LayoutInflater.from(binding.root.context)
                val chip = (layoutInflater.inflate(R.layout.item_chip, null) as Chip).apply {
                    id = View.generateViewId()
                    isChecked = true
                    isClickable = false
                    text = item.name
                }
                binding.chipGroupInterests.addView(chip)
            }
        }
    }
}
