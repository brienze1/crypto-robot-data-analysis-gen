package org.brienze.crypto.data.analysis.enums

enum class Interval(
    val value: String,
    val description: String
) {
    ONE_MINUTE("1m", "1 minute interval"),
    FIVE_MINUTES("5m", "5 minutes interval"),
    FIFTEEN_MINUTES("15m", "15 minutes interval"),
    THIRTY_MINUTES("30m", "30 minutes interval"),
    ONE_HOUR("1h", "1 hour interval"),
    SIX_HOURS("6h", "6 hours interval"),
    ONE_DAY("1d", "1 day interval")

}
