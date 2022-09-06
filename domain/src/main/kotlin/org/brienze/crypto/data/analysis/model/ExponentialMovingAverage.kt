package org.brienze.crypto.data.analysis.model

import org.brienze.crypto.data.analysis.enums.Period
import java.math.BigDecimal
import java.math.RoundingMode

class ExponentialMovingAverage(
    totalValue: BigDecimal,
    period: Period,
    lastCandle: Candle,
    currentCandle: Candle,
) : MovingAverage(period, currentCandle) {

    init {
        value = calculateExponentialMovingAverage(totalValue, period, lastCandle).setScale(5, RoundingMode.UP)
    }
}
