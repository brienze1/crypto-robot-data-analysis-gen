package org.brienze.crypto.data.analysis.model

import org.brienze.crypto.data.analysis.enums.Period
import java.math.BigDecimal
import java.math.RoundingMode

class SimpleMovingAverage(
    totalValue: BigDecimal,
    period: Period,
    currentCandle: Candle,
) : MovingAverage(period, currentCandle) {

    init {
        value = calculateSimpleMovingAverage(totalValue, period).setScale(5, RoundingMode.UP)
    }
}
