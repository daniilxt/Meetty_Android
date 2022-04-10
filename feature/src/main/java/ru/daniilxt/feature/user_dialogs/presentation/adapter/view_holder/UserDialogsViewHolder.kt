package ru.daniilxt.feature.user_dialogs.presentation.adapter.view_holder

import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.feature.databinding.ItemDialogBinding
import ru.daniilxt.feature.domain.model.User
import ru.daniilxt.feature.domain.model.UserDialog

class UserDialogsViewHolder(
    private val binding: ItemDialogBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: UserDialog) {
        binding.tvTitle.text =
            data.returnCompanionUser(User(0, "", "", "")).getCapitalizedFullUserName()
        binding.tvMessage.text = data.lastMessage.content
        binding.tvTime.text = data.lastMessage.time.toString()
        //binding.ivIcon.load(data.photo)
    }
}