package ru.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReactionWrapper(
    val messageId: Long,
    val reaction: Reaction
) : Parcelable
