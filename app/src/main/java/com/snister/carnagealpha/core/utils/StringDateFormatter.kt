package com.snister.carnagealpha.core.utils

import java.text.NumberFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class StringDateFormatter{
    companion object{
        fun toStandardDateFormat(date: ZonedDateTime): String{
            val formattedZonedDateTime = DateTimeFormatter
                .ofPattern("dd MMM yyyy").format(date)

            return formattedZonedDateTime.toString()
        }

        fun toDayMonthYearAtHourMinute(date: ZonedDateTime): String{
            val formattedDayMonthYear = DateTimeFormatter
                .ofPattern("dd MMM yyyy").format(date)

            val formatted24HourMinutes = DateTimeFormatter
                .ofPattern("HH:mm").format(date)

            return "$formattedDayMonthYear at $formatted24HourMinutes"
        }
    }
}