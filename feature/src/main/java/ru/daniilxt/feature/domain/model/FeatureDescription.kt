package ru.daniilxt.feature.domain.model

import android.graphics.drawable.Drawable

data class FeatureDescription(
    val id: Long,
    val title: String,
    val description: String,
    val photo: Drawable?,
)