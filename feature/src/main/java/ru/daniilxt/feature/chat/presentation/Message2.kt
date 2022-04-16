package ru.daniilxt.feature.chat.presentation

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.ZoneOffset

@SuppressLint("NewApi")
data class Message2(
    var datetime: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC),
    val text: String,
    val author: String,
    var receiver: String? = null
)