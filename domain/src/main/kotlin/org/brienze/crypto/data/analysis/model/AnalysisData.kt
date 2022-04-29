package org.brienze.crypto.data.analysis.model

import org.brienze.crypto.data.analysis.enums.Period
import java.math.BigDecimal

class AnalysisData(
    listOfCandles: List<Candle>
) {

    val simpleMovingAverages: MutableMap<Period, SimpleMovingAverage> = mutableMapOf()
    val exponentialMovingAverages: MutableMap<Period, ExponentialMovingAverage> = mutableMapOf()

    init {
        var i = 0
        var totalSum: BigDecimal = BigDecimal.ZERO
        while(listOfCandles.getOrNull(i) != null && listOfCandles.getOrNull(i+1) != null && i < 200){
            totalSum += listOfCandles[i].close
            val lastCandle = listOfCandles[i+1]
            val currentCandle = listOfCandles[i]

            when(i + 1){
                5 -> {
                    simpleMovingAverages[Period.FIVE] = SimpleMovingAverage(totalSum, Period.FIVE, lastCandle, currentCandle)
                    exponentialMovingAverages[Period.FIVE] = ExponentialMovingAverage(totalSum, Period.FIVE, lastCandle, currentCandle)
                }
                10 -> {
                    simpleMovingAverages[Period.TEN] = SimpleMovingAverage(totalSum, Period.TEN, lastCandle, currentCandle)
                    exponentialMovingAverages[Period.TEN] = ExponentialMovingAverage(totalSum, Period.TEN, lastCandle, currentCandle)
                }
                20 -> {
                    simpleMovingAverages[Period.TWENTY] = SimpleMovingAverage(totalSum, Period.TWENTY, lastCandle, currentCandle)
                    exponentialMovingAverages[Period.TWENTY] = ExponentialMovingAverage(totalSum, Period.TWENTY, lastCandle, currentCandle)
                }
                50 -> {
                    simpleMovingAverages[Period.FIFTY] = SimpleMovingAverage(totalSum, Period.FIFTY, lastCandle, currentCandle)
                    exponentialMovingAverages[Period.FIFTY] = ExponentialMovingAverage(totalSum, Period.FIFTY, lastCandle, currentCandle)
                }
                100 -> {
                    simpleMovingAverages[Period.HUNDRED] = SimpleMovingAverage(totalSum, Period.HUNDRED, lastCandle, currentCandle)
                    exponentialMovingAverages[Period.HUNDRED] = ExponentialMovingAverage(totalSum, Period.HUNDRED, lastCandle, currentCandle)
                }
                200 -> {
                    simpleMovingAverages[Period.TWO_HUNDRED] = SimpleMovingAverage(totalSum, Period.TWO_HUNDRED, lastCandle, currentCandle)
                    exponentialMovingAverages[Period.TWO_HUNDRED] = ExponentialMovingAverage(totalSum, Period.TWO_HUNDRED, lastCandle, currentCandle)
                }
            }

            i++
        }
    }

}