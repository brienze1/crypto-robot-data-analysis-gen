package org.brienze.crypto.data.analysis.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import org.brienze.crypto.data.analysis.adapter.CandleServiceAdapter
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.enums.Symbol
import org.brienze.crypto.data.analysis.service.AnalysisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UpdateAnalysisUseCase {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var candleService: CandleServiceAdapter

    @Autowired
    lateinit var analysisService: AnalysisService

    fun update(interval: Interval) {
        println("Initializing " + interval.description + " with value " + interval.value)

        val listOfCandles = candleService.getLastCandlesByInterval(500, interval, Symbol.BTCUSDT)

        val analysisIndicators = analysisService.createAnalysisIndicators(listOfCandles, interval)

        //Save and/or send
        println(objectMapper.writeValueAsString(analysisIndicators))
    }

}