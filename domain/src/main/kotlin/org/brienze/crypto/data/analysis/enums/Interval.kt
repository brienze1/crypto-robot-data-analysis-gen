package org.brienze.crypto.data.analysis.enums

enum class Interval(
    val value: String,
    val intervalDropValue: Int,
    val description: String
) {
    ONE_MINUTE("1m", 15,"1 minute interval"),
    FIVE_MINUTES("5m", 15, "5 minutes interval"),
    FIFTEEN_MINUTES("15m", 15,"15 minutes interval"),
    THIRTY_MINUTES("30m", 15, "30 minutes interval"),
    ONE_HOUR("1h", 15, "1 hour interval"),
    SIX_HOURS("6h", 15, "6 hours interval"),
    ONE_DAY("1d", 15, "1 day interval")

}
