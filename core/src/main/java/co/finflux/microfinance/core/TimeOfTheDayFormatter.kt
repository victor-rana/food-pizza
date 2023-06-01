package co.app.food.core

import java.util.Calendar

object TimeOfTheDayFormatter {
    fun fetch(): TimeOfTheDay {
        val c: Calendar = Calendar.getInstance()
        return when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> {
                TimeOfTheDay.Morning
            }
            in 12..15 -> {
                TimeOfTheDay.Afternoon
            }
            in 16..20 -> {
                TimeOfTheDay.Evening
            }
            else -> {
                TimeOfTheDay.Night
            }
        }
    }
}
