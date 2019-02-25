package com.github.icarohs7.unoxandroidarch.extensions

import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import arrow.core.getOrElse
import khronos.toDate
import khronos.toString
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

/** The actual date */
val now: Date
    get() = Date()

/** Convert a date to it's String form in the format [DateFormat.REMOTE_DATETIME] */
val Date.asRemoteDateTime: String
    get() = toString(DateFormat.REMOTE_DATETIME)

/** Convert a date to it's String form in the format [DateFormat.REMOTE_DATE] */
val Date.asRemoteDate: String
    get() = toString(DateFormat.REMOTE_DATE)

/** Convert a date to it's String form in the format [DateFormat.REMOTE_TIME] */
val Date.asRemoteTime: String
    get() = toString(DateFormat.REMOTE_TIME)

/** Convert a date to it's String form in the format [DateFormat.BR_DATETIME] */
val Date.asBrDateTime: String
    get() = toString(DateFormat.BR_DATETIME)

/** Convert a date to it's String form in the format [DateFormat.BR_DATE] */
val Date.asBrDate: String
    get() = toString(DateFormat.BR_DATE)

/** Convert a date to it's String form in the format [DateFormat.SHORT_TIME] */
val Date.asShortTime: String
    get() = toString(DateFormat.SHORT_TIME)

/** Return the year value of the given date */
val Date.yearValue: String
    get() = "${Calendar.getInstance().apply { time = this@yearValue }.get(Calendar.YEAR)}"

/** Return the month value of the given date, starting by 1 for january to 12 for december */
val Date.monthValue: String
    get() = "${Calendar.getInstance().apply { time = this@monthValue }.get(Calendar.MONTH) + 1}".padStart(2, '0')

/** Return the day value of the given date */
val Date.dayValue: String
    get() = "${Calendar.getInstance().apply { time = this@dayValue }.get(Calendar.DAY_OF_MONTH)}".padStart(2, '0')

/** @return The month day of this date, starting by 1 */
val Date.dayNumber: Int
    get() = toString("dd").toInt()

/**
 * Convert a string in the given format to a [Date] instance or try to convert from
 * a list of known formats
 */
fun String.asDate(format: String = DateFormat.REMOTE_DATETIME): Date {
    val try1 = Try { this.toDate(format) }
    val formats = DateFormat.run {
        listOf(
                REMOTE_DATETIME,
                BR_DATETIME,
                REMOTE_DATE,
                BR_DATE,
                REMOTE_TIME,
                SHORT_TIME)
    }
    val date = formats.fold(try1) { acc, f ->
        when (acc) {
            is Failure -> Try { this.toDate(f) }
            is Success -> acc
        }
    }
    return date.getOrElse { Date(0) }
}

/**
 * Convert the [Date] object to the given format without converting the instance type
 * @return The new instance of [Date] with the given format
 */
fun Date.map(newFormat: String): Date =
        toString(newFormat).asDate(newFormat)

/**
 * Convert the number representing an interval in miliseconds to the string
 * representing how much time is left in the format HH:mm:ss
 */
fun Long.toTimeLeft(): String {
    if (this <= 0) return ""
    val h = "${TimeUnit.MILLISECONDS.toHours(this)}".padStart(2, '0')
    val m = "${TimeUnit.MILLISECONDS.toMinutes(this) % 60}".padStart(2, '0')
    val s = "${TimeUnit.MILLISECONDS.toSeconds(this) % 60}".padStart(2, '0')
    return when {
        this >= 3_600_000 -> "$h:$m:$s"
        this >= 60_000 -> "$m:$s"
        else -> s
    }
}

/** Date formats used throughout the applications */
object DateFormat {
    const val REMOTE_DATETIME: String = "yyyy-MM-dd HH:mm:ss"
    const val REMOTE_DATE: String = "yyyy-MM-dd"
    const val REMOTE_TIME: String = "HH:mm:ss"

    const val BR_DATETIME: String = "dd/MM/yyyy HH:mm:ss"
    const val BR_DATE: String = "dd/MM/yyyy"

    const val SHORT_TIME: String = "HH:mm"
}