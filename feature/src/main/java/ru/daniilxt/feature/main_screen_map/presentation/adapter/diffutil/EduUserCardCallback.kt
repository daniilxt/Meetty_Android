package ru.daniilxt.feature.main_screen_map.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.daniilxt.feature.domain.model.MapSimpleUser

class EduUserCardCallback : DiffUtil.ItemCallback<MapSimpleUser>() {
    override fun areItemsTheSame(
        oldItem: MapSimpleUser,
        newItem: MapSimpleUser
    ): Boolean {
        return oldItem.userInfo.id == newItem.userInfo.id
    }

    override fun areContentsTheSame(
        oldItem: MapSimpleUser,
        newItem: MapSimpleUser
    ): Boolean {
        return oldItem.userInfo.id == newItem.userInfo.id &&
            oldItem.userAdditionalInfo == newItem.userAdditionalInfo
    }
}
