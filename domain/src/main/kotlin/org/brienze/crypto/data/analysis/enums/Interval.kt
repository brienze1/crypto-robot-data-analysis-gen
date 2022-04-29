package org.brienze.crypto.data.analysis.enums

enum class Interval(
    val value: String,
    val intervalDropValue: Int,
    val description: String
) {
    ONE_MINUTE("1m", 10,"1 minute interval"),
    FIVE_MINUTES("5m", 10, "5 minutes interval"),
    FIFTEEN_MINUTES("15m", 10,"15 minutes interval"),
    THIRTY_MINUTES("30m", 10, "30 minutes interval"),
    ONE_HOUR("1h", 10, "1 hour interval"),
    SIX_HOURS("6h", 10, "6 hours interval"),
    ONE_DAY("1d", 10, "1 day interval")
}
