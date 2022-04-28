package org.brienze.crypto.data.analysis.model

import org.brienze.crypto.data.analysis.enums.Period
import java.math.BigDecimal

class SimpleMovingAverage(
    totalValue: BigDecimal,
    period: Period,
    lastCandle: Candle,
    currentCandle: Candle,
) : MovingAverage(period, lastCandle, currentCandle) {

    init {
        value = calculateSimpleMovingAverage(totalValue, period)
    }

}