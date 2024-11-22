package frgp.utn.edu.ar.quepasa.utils.date

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.pow

fun formatNumber(number: Int): String {
    if (number < 1000) return "$number"
    val exp = (ln(abs(number.toDouble())) / ln(1000.0)).toInt()
    val suffix = "KMBT"[exp - 1]
    return String.format("%.1f%s", number / 1000.0.pow(exp.toDouble()), suffix)
}

fun Timestamp.formatTimeAgo(): String {
    val now = System.currentTimeMillis()
    val diff = now - this.time

    val days = TimeUnit.MILLISECONDS.toDays(diff)
    val months = days / 30
    val years = days / 365

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "justo ahora"
        diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)}m atrás"
        diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)}h atrás"
        days < 7 -> "${days}d atrás"
        months < 12 -> "${months}m atrás"
        years < 100 -> "${years}a atrás"
        else -> {
            val dateFormat = SimpleDateFormat("d M yy", Locale("es", "ES"))
            dateFormat.format(this)
        }
    }
}
