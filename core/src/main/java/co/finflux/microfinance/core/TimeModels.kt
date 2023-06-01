package co.app.food.core

sealed class TimeOfTheDayIcon {
    object MorningIcon : TimeOfTheDayIcon()
    object AfternoonIcon : TimeOfTheDayIcon()
    object EveningIcon : TimeOfTheDayIcon()
    object NightIcon : TimeOfTheDayIcon()
}

sealed class TimeOfTheDay(val message: String, val iconId: TimeOfTheDayIcon) {
    object Morning : TimeOfTheDay(message = "Good Morning", iconId = TimeOfTheDayIcon.MorningIcon)
    object Afternoon : TimeOfTheDay(
        message = "Good Afternoon",
        iconId = TimeOfTheDayIcon.AfternoonIcon
    )

    object Evening : TimeOfTheDay(message = "Good Evening", iconId = TimeOfTheDayIcon.EveningIcon)
    object Night : TimeOfTheDay(message = "Good Night", iconId = TimeOfTheDayIcon.NightIcon)
}
