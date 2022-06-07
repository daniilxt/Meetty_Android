package ru.daniilxt.feature.main_screen_map.presentation.adapter.view_holder

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.ItemEduUserCardBinding
import ru.daniilxt.feature.domain.model.MapSimpleUser

class EduUserCardViewHolder(
    private val binding: ItemEduUserCardBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: MapSimpleUser, onCardClickListener: (data: MapSimpleUser) -> Unit) {
        with(binding) {
            ivAvatar.load(data.userInfo.avatarLink) {
                transformations(CircleCropTransformation())
            }
            tvFullName.text = data.userInfo.getFullUserName()
            chipGroupInterests.removeAllViews()
            for (item in data.userAdditionalInfo.categories) {
                val layoutInflater = LayoutInflater.from(binding.root.context)
                val chip = (layoutInflater.inflate(R.layout.item_chip, null) as Chip).apply {
                    id = View.generateViewId()
                    isChecked = true
                    isClickable = false
                    setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.sp_12)
                    )
                    text = item.interestName
                }
                binding.chipGroupInterests.addView(chip)
            }
        }
        itemView.setDebounceClickListener {
            onCardClickListener(data)
        }
    }
}
