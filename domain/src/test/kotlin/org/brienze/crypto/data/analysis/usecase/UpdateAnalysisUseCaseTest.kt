package org.brienze.crypto.data.analysis.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.brienze.crypto.data.analysis.adapter.AnalysisEventAdapter
import org.brienze.crypto.data.analysis.adapter.CandleServiceAdapter
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.enums.Symbol
import org.brienze.crypto.data.analysis.model.AnalysisData
import org.brienze.crypto.data.analysis.model.AnalysisIndicators
import org.brienze.crypto.data.analysis.model.Candle
import org.brienze.crypto.data.analysis.service.AnalysisService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class UpdateAnalysisUseCaseTest {

    @InjectMockKs
    private lateinit var updateAnalysisUseCase: UpdateAnalysisUseCase

    @MockK
    private lateinit var candleService: CandleServiceAdapter

    @MockK
    private lateinit var analysisService: AnalysisService

    @MockK
    private lateinit var analysisEventService: AnalysisEventAdapter

    private lateinit var interval: Interval
    private lateinit var listOfCandles: List<Candle>
    private lateinit var analysisIndicators: AnalysisIndicators

    @BeforeEach
    fun init() {
        interval = Interval.SIX_HOURS

        listOfCandles = listOf()

        analysisIndicators = AnalysisIndicators(interval, AnalysisData(listOfCandles), AnalysisData(listOfCandles))
    }

    @Test
    fun updateTest() {
        every { candleService.getLastCandlesByInterval(500, interval, Symbol.BTCUSDT) }.returns(listOfCandles)
        every { analysisService.createAnalysisIndicators(listOfCandles, interval) }.returns(analysisIndicators)
        every { analysisEventService.sendEvent(analysisIndicators) }.returns(Unit)

        updateAnalysisUseCase.update(Interval.SIX_HOURS)

        verify(exactly = 1) { candleService.getLastCandlesByInterval(500, interval, Symbol.BTCUSDT) }
        verify(exactly = 1) { analysisService.createAnalysisIndicators(listOfCandles, interval) }
        verify(exactly = 1) { analysisEventService.sendEvent(analysisIndicators) }
    }

}