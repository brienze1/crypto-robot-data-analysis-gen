package org.brienze.crypto.data.analysis.service

import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.model.AnalysisData
import org.brienze.crypto.data.analysis.model.AnalysisIndicators
import org.brienze.crypto.data.analysis.model.Candle
import org.springframework.stereotype.Component

@Component
class AnalysisService {

    fun createAnalysisIndicators(listOfCandles: List<Candle>, interval: Interval): AnalysisIndicators {
        val lastAnalysisData = AnalysisData(listOfCandles.subList(interval.intervalDropValue, listOfCandles.size-1))
        val currentAnalysisData = AnalysisData(listOfCandles)

        return AnalysisIndicators(interval, currentAnalysisData, lastAnalysisData)
    }

}