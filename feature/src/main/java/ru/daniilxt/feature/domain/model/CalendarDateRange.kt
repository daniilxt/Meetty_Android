package ru.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class CalendarDateRange(
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null
) : Parcelable
