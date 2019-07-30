package com.guideme.guideme.ui.common

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {
        fun formatDate(date: Date): String {
            return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date)
        }

        fun forwardDaysFromTomorrow(days: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, 1 + days)
            return calendar.time
        }

        fun getYear(date: Date): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar.get(Calendar.YEAR)
        }

        fun getMonth(date: Date): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar.get(Calendar.MONTH)
        }

        fun getDayOfMonth(date: Date): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar.get(Calendar.DAY_OF_MONTH)
        }
    }
}
