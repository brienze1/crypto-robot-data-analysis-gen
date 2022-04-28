package org.brienze.crypto.data.analysis.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.brienze.crypto.data.analysis.enums.Indicator
import org.brienze.crypto.data.analysis.enums.Period
import org.springframework.beans.factory.annotation.Value
import java.math.BigDecimal

abstract class MovingAverage(
    private val period: Period,
    @JsonIgnore private val lastCandle: Candle,
    @JsonIgnore private val currentCandle: Candle,
) {

    @Value("\${smoothening.factor.value:0.3}")
    @JsonIgnore
    private lateinit var smootheningFactor: BigDecimal

    lateinit var value: BigDecimal
        protected set

    private lateinit var indicator: Indicator

    fun calculateIndicator(lastMovingAverages: Map<Period, MovingAverage>, currentMovingAverages: Map<Period, MovingAverage>) {
        var indicatorValue = 0
        if (lastCandle.close < value && currentCandle.close > value) {
            indicatorValue++
        } else if (lastCandle.close > value && currentCandle.close < value) {
            indicatorValue--
        }

        for(period: Period in Period.values()) {
            if(this.period.value < period.value) {
                if(value < lastMovingAverages.getValue(period).value && value > currentMovingAverages.getValue(period).value) {
                    indicatorValue++
                } else if(value > lastMovingAverages.getValue(period).value && value < currentMovingAverages.getValue(period).value) {
                    indicatorValue--
                }
            } else if(this.period.value > period.value) {
                if(value < lastMovingAverages.getValue(period).value && value > currentMovingAverages.getValue(period).value) {
                    indicatorValue--
                } else if(value > lastMovingAverages.getValue(period).value && value < currentMovingAverages.getValue(period).value) {
                    indicatorValue++
                }
            }
        }

        indicator = when {
            indicatorValue >= 4 -> Indicator.STRONG_BUY
            indicatorValue >= 1 -> Indicator.BUY
            indicatorValue <= -4 -> Indicator.STRONG_SELL
            indicatorValue <= -1 -> Indicator.SELL
            else -> Indicator.NEUTRAL
        }
    }

    protected fun calculateSimpleMovingAverage(totalValue: BigDecimal, period: Period): BigDecimal {
        return totalValue.divide(BigDecimal(period.value))
    }

    protected fun calculateExponentialMovingAverage(totalValue: BigDecimal, period: Period, lastCandle: Candle): BigDecimal {
        return smootheningFactor.times(lastCandle.close) + (BigDecimal.ONE.minus(smootheningFactor)) * calculateSimpleMovingAverage(totalValue, period)
    }

}