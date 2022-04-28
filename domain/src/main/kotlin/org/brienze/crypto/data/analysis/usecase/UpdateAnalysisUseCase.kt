package org.brienze.crypto.data.analysis.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import org.brienze.crypto.data.analysis.adapter.CandleServiceAdapter
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.model.AnalysisData
import org.brienze.crypto.data.analysis.model.AnalysisIndicators
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UpdateAnalysisUseCase {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var candleService: CandleServiceAdapter

    fun update(interval: Interval) {
        println("Initializing " + interval.description + " with value " + interval.value)

        val listOfCandles = candleService.getLastCandlesByInterval(202, interval)

        //service
        val lastAnalysisData = AnalysisData(listOfCandles.sortedByDescending { candle -> candle.closeTime }.dropLast(1))
        //service
        val currentAnalysisData = AnalysisData(listOfCandles)
        //service
        val analysisIndicators = AnalysisIndicators(interval, currentAnalysisData, lastAnalysisData)

        println(objectMapper.writeValueAsString(analysisIndicators))
    }

}