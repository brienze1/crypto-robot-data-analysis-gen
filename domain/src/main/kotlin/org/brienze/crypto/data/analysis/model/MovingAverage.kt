package org.brienze.crypto.data.analysis.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.brienze.crypto.data.analysis.enums.Factor
import org.brienze.crypto.data.analysis.enums.Indicator
import org.brienze.crypto.data.analysis.enums.Period
import java.math.BigDecimal

abstract class MovingAverage(
    private val period: Period,
    @JsonIgnore private val currentCandle: Candle,
) {

    lateinit var value: BigDecimal
        protected set

    private lateinit var indicator: Indicator

    fun calculateIndicator(lastMovingAverages: Map<Period, MovingAverage>, currentMovingAverages: Map<Period, MovingAverage>) {
        var indicatorValue = 0
        if (lastMovingAverages.getValue(this.period).currentCandle.close < value && currentCandle.close > value) {
            indicatorValue++
        } else if (lastMovingAverages.getValue(this.period).currentCandle.close > value && currentCandle.close < value) {
            indicatorValue--
        }

        for(period: Period in Period.values()) {
            if(this.period.value < period.value) {
                if(lastMovingAverages.getValue(this.period).value < lastMovingAverages.getValue(period).value && value > currentMovingAverages.getValue(period).value) {
                    indicatorValue += 2
                } else if(lastMovingAverages.getValue(this.period).value > lastMovingAverages.getValue(period).value && value < currentMovingAverages.getValue(period).value) {
                    indicatorValue -= 2
                }
            } else if(this.period.value > period.value) {
                if(lastMovingAverages.getValue(this.period).value < lastMovingAverages.getValue(period).value && value > currentMovingAverages.getValue(period).value) {
                    indicatorValue -= 2
                } else if(lastMovingAverages.getValue(this.period).value > lastMovingAverages.getValue(period).value && value < currentMovingAverages.getValue(period).value) {
                    indicatorValue += 2
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
        return Factor.SMOOTHENING_FACTOR.value.times(lastCandle.close) + (BigDecimal.ONE.minus(Factor.SMOOTHENING_FACTOR.value)) * calculateSimpleMovingAverage(totalValue, period)
    }

}