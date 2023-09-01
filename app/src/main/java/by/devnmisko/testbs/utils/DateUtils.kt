package by.devnmisko.testbs.utils

import by.devnmisko.testbs.utils.Const.Pattern.DATE_PATTERN
import by.devnmisko.testbs.utils.Const.Pattern.DATE_TIME_PATTERN
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getDate(milliSeconds: Long): String? {
    return getDateWithPattern(milliSeconds, DATE_PATTERN)
}
fun getDateTime(milliSeconds: Long): String? {
    return getDateWithPattern(milliSeconds, DATE_TIME_PATTERN)
}
fun getDateWithPattern(milliSeconds: Long, pattern: String): String? {
    // Create a DateFormatter object for displaying date in specified format.
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}