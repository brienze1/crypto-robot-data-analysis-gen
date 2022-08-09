package org.brienze.crypto.data.analysis.service

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.model.AnalysisData
import org.brienze.crypto.data.analysis.model.AnalysisIndicators
import org.brienze.crypto.data.analysis.model.Candle
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class AnalysisServiceTest {

    @InjectMockKs
    private lateinit var analysisService: AnalysisService

    private lateinit var listOfCandles: MutableList<Candle>
    private lateinit var interval: Interval

    @BeforeEach
    fun init() {
        listOfCandles = mutableListOf()

        for (i in 1..500) {
            listOfCandles.add(
                Candle(
                    LocalDateTime.now(),
                    BigDecimal(Random(9999).nextDouble()),
                    BigDecimal(Random(9999).nextDouble()),
                    BigDecimal(Random(9999).nextDouble()),
                    BigDecimal(Random(9999).nextDouble()),
                    BigDecimal(Random(9999).nextDouble()),
                    LocalDateTime.now(),
                    BigDecimal(Random(9999).nextDouble()),
                    BigInteger(Random(9999).nextLong().toString()),
                    BigDecimal(Random(9999).nextDouble()),
                    BigDecimal(Random(9999).nextDouble()),
                    BigDecimal(Random(9999).nextDouble()).toString(),
                    BigDecimal(Random(9999).nextDouble()).toString(),
                )
            )
        }

        interval = Interval.SIX_HOURS
    }

    @Test
    fun createAnalysisIndicatorsTest() {
        val lastAnalysisData = AnalysisData(listOfCandles.subList(interval.intervalDropValue, listOfCandles.size - 1))
        val currentAnalysisData = AnalysisData(listOfCandles)
        val expectedAnalysisIndicators = AnalysisIndicators(interval, currentAnalysisData, lastAnalysisData)

        val analysisIndicators = analysisService.createAnalysisIndicators(listOfCandles, interval)

        Assertions.assertThat(analysisIndicators).usingRecursiveComparison().isEqualTo(expectedAnalysisIndicators)
    }

}