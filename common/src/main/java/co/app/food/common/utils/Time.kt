package co.app.food.common.utils

import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

interface Time {
    fun now(): Long
    fun zonedDateTimeNowInUtc(): ZonedDateTime
}

class TimeImpl : Time {
    override fun now(): Long {
        return System.currentTimeMillis()
    }

    override fun zonedDateTimeNowInUtc(): ZonedDateTime {
        return ZonedDateTime.now(ZoneId.of("UTC"))
    }
}
