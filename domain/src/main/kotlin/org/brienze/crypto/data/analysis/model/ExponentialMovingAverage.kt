package org.brienze.crypto.data.analysis.model

import org.brienze.crypto.data.analysis.enums.Period
import java.math.BigDecimal

class ExponentialMovingAverage(
    totalValue: BigDecimal,
    period: Period,
    lastCandle: Candle,
    currentCandle: Candle,
) : MovingAverage(period, lastCandle, currentCandle) {

    init {
        value = calculateExponentialMovingAverage(totalValue, period, lastCandle)
    }

}