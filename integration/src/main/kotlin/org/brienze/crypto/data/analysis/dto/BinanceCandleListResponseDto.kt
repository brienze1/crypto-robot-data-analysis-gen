package org.brienze.crypto.data.analysis.dto

import org.brienze.crypto.data.analysis.enums.Symbol
import org.brienze.crypto.data.analysis.model.Candle

class BinanceCandleListResponseDto(
    candleObjects: Array<Array<String>>,
    symbol: Symbol
) {

    val candles: MutableList<BinanceCandleDto> = mutableListOf()

    init {
        for (candleObject: Array<String> in candleObjects) {
            candles.add(BinanceCandleDto(candleObject, symbol))
        }
    }

    fun parseToCandleList(): List<Candle> {
        val candleList = mutableListOf<Candle>()

        for (binanceCandle: BinanceCandleDto in candles) {
            candleList.add(binanceCandle.toCandle())
        }

        return candleList
    }
}
