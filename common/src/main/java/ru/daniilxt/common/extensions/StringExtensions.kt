package ru.daniilxt.common.extensions

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.getAgeFromNumber(age: Int): String {
    val result: String
    val units: List<Int> = listOf(2, 3, 4)
    val dozens: List<Int> = listOf(11, 12, 13, 14)
    result = if (age != 11 && age % 10 == 1) {
        "$age год"
    } else if (age % 1 != 0 || units.contains(age % 10) && !dozens.contains(age % 100)) {
        "$age года"
    } else {
        "$age лет"
    }
    return result
}

@SuppressLint("NewApi")
fun String.parseLocalDate(): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
