package ru.daniilxt.feature.user_dialogs.presentation.adapter.view_holder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.databinding.ItemDialogBinding
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider

@SuppressLint("NewApi")

class UserDialogsViewHolder(
    private val binding: ItemDialogBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: UserDialog, onDialogClick: (userDialog: UserDialog) -> Unit) {
        // TODO edit
        val companion = data.returnCompanionUser(UserDialogsProvider.myUser)
        binding.tvTitle.text = companion.getCapitalizedFullUserName()
        binding.tvMessage.text = data.lastMessage.content

        val time = data.lastMessage.dateTime.toLocalTime()
        binding.tvTime.text = time.toString()
        binding.ivIcon.load(companion.avatarLink) {
            transformations(CircleCropTransformation())
        }
        itemView.setDebounceClickListener {
            onDialogClick(data)
        }
    }
}
