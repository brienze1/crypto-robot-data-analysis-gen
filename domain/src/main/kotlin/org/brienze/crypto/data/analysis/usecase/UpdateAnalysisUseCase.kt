package org.brienze.crypto.data.analysis.usecase

import org.brienze.crypto.data.analysis.adapter.AnalysisEventAdapter
import org.brienze.crypto.data.analysis.adapter.CandleServiceAdapter
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.enums.Symbol
import org.brienze.crypto.data.analysis.service.AnalysisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UpdateAnalysisUseCase {

    @Autowired
    private lateinit var candleService: CandleServiceAdapter

    @Autowired
    private lateinit var analysisService: AnalysisService

    @Autowired
    private lateinit var analysisEventService: AnalysisEventAdapter

    fun update(interval: Interval) {
        //TODO add loggers
        println("Initializing " + interval.description + " with value " + interval.value)

        val listOfCandles = candleService.getLastCandlesByInterval(500, interval, Symbol.BTCUSDT)

        val analysisIndicators = analysisService.createAnalysisIndicators(listOfCandles, interval)

        analysisEventService.sendEvent(analysisIndicators)
    }

}