package org.brienze.crypto.data.analysis.model

import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime

class Candle(
    val openTime: LocalDateTime,
    val open: BigDecimal,
    val high: BigDecimal,
    val low: BigDecimal,
    val close: BigDecimal,
    val volume: BigDecimal,
    val closeTime: LocalDateTime,
    val quoteAssetVolume: BigDecimal,
    val numberOfTrades: BigInteger,
    val buyBaseAssetVolume: BigDecimal,
    val buyQuoteAssetVolume: BigDecimal,
    val base: String,
    val quote: String,
)
