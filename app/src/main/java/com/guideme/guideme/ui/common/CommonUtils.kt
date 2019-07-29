package com.guideme.guideme.ui.common

import java.util.*

class CommonUtils {

    companion object {
        fun forwardDaysFromTomorrow(days: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, 1 + days)
            return calendar.time
        }
    }
}
