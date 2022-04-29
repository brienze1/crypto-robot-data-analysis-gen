package org.brienze.crypto.data.analysis.enums

enum class Symbol(
    val value: String,
    val base: String,
    val quote: String
) {
    BTCUSDT("BTCUSDT", "USDT", "BTC")
}