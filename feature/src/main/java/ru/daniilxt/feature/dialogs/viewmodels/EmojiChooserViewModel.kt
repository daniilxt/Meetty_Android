package ru.daniilxt.feature.dialogs.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.feature.domain.model.Reaction
import ru.daniilxt.feature.utils.ReactionFactory

class EmojiChooserViewModel : ViewModel() {

    var messageId: Long = -1

    private val _emojiList: MutableStateFlow<List<Reaction>> = MutableStateFlow(emptyList())
    val emojiListLiveData: StateFlow<List<Reaction>> get() = _emojiList

    init {
        _emojiList.value = ReactionFactory.getReactions()
    }
}
