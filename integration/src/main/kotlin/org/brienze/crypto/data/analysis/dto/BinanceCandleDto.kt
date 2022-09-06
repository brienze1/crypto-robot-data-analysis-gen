package org.brienze.crypto.data.analysis.dto

import org.brienze.crypto.data.analysis.enums.Symbol
import org.brienze.crypto.data.analysis.exception.BinanceCandleMissingValueException
import org.brienze.crypto.data.analysis.model.Candle
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class BinanceCandleDto(
    values: Array<String>,
    private val symbol: Symbol
) {

    private val openTime: LocalDateTime
    private val open: BigDecimal
    private val high: BigDecimal
    private val low: BigDecimal
    private val close: BigDecimal
    private val volume: BigDecimal
    private val closeTime: LocalDateTime
    private val quoteAssetVolume: BigDecimal
    private val numberOfTrades: BigInteger
    private val buyBaseAssetVolume: BigDecimal
    private val buyQuoteAssetVolume: BigDecimal

    init {
        openTime = toLocalDateTime(values.getOrElse(0) { throw BinanceCandleMissingValueException("openTime") })
        open = values.getOrElse(1) { throw BinanceCandleMissingValueException("open") }.toBigDecimal()
        high = values.getOrElse(2) { throw BinanceCandleMissingValueException("high") }.toBigDecimal()
        low = values.getOrElse(3) { throw BinanceCandleMissingValueException("low") }.toBigDecimal()
        close = values.getOrElse(4) { throw BinanceCandleMissingValueException("close") }.toBigDecimal()
        volume = values.getOrElse(5) { throw BinanceCandleMissingValueException("volume") }.toBigDecimal()
        closeTime = toLocalDateTime(values.getOrElse(6) { throw BinanceCandleMissingValueException("closeTime") })
        quoteAssetVolume =
            values.getOrElse(7) { throw BinanceCandleMissingValueException("quoteAssetVolume") }.toBigDecimal()
        numberOfTrades =
            values.getOrElse(8) { throw BinanceCandleMissingValueException("numberOfTrades") }.toBigInteger()
        buyBaseAssetVolume =
            values.getOrElse(9) { throw BinanceCandleMissingValueException("buyBaseAssetVolume") }.toBigDecimal()
        buyQuoteAssetVolume =
            values.getOrElse(10) { throw BinanceCandleMissingValueException("buyQuoteAssetVolume") }.toBigDecimal()
    }

    private fun toLocalDateTime(millis: String): LocalDateTime {
        return Instant.ofEpochMilli(millis.toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    fun toCandle(): Candle {
        return Candle(
            openTime,
            open,
            high,
            low,
            close,
            volume,
            closeTime,
            quoteAssetVolume,
            numberOfTrades,
            buyBaseAssetVolume,
            buyQuoteAssetVolume,
            symbol.base,
            symbol.quote
        )
    }
}
