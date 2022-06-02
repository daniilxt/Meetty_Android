package ru.daniilxt.common.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.toDate(): Date {
    return Date.from(atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
}

fun LocalDate.toSendDateString(): String =
    format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
