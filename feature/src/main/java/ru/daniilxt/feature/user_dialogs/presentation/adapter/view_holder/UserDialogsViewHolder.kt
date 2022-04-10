package ru.daniilxt.feature.user_dialogs.presentation.adapter.view_holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ru.daniilxt.feature.databinding.ItemDialogBinding
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider

class UserDialogsViewHolder(
    private val binding: ItemDialogBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: UserDialog) {
        // TODO edit
        val companion = data.returnCompanionUser(UserDialogsProvider.myUser)
        binding.tvTitle.text = companion.getCapitalizedFullUserName()
        binding.tvMessage.text = data.lastMessage.content
        binding.tvTime.text = data.lastMessage.time.toString()
        binding.ivIcon.load(companion.avatarLink) {
            transformations(CircleCropTransformation())
        }
    }
}
