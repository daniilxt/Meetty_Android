package ru.daniilxt.feature.user_dialogs.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemDialogBinding
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.user_dialogs.presentation.adapter.diffutil.UserDialogsCallback
import ru.daniilxt.feature.user_dialogs.presentation.adapter.view_holder.UserDialogsViewHolder
import timber.log.Timber

class UserDialogsAdapter : RecyclerView.Adapter<UserDialogsViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserDialogsViewHolder =
        UserDialogsViewHolder(parent.viewBinding(ItemDialogBinding::inflate))

    override fun onBindViewHolder(holder: UserDialogsViewHolder, position: Int) {
        Timber.i("${differ.currentList[position]}")
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun bind(data: List<UserDialog>) {
        differ.submitList(data)
    }

    companion object {
        private val DIFF_CALLBACK = UserDialogsCallback()
    }
}