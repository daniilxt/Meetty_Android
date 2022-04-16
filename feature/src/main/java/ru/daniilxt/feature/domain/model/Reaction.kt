package ru.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reaction(
    val id: Long,
    val emojiText: String,
    val usersId: List<Long>,
    val count: Long
) : Parcelable
