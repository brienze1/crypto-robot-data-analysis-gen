package org.brienze.crypto.data.analysis.model

import org.brienze.crypto.data.analysis.enums.Interval
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AnalysisIndicators(
    val interval: Interval,
    currentAnalysisData: AnalysisData,
    lastAnalysisData: AnalysisData
) {

    val analysisData: AnalysisData
    val timestamp: String

    init {
        currentAnalysisData.simpleMovingAverages
            .values
            .forEach { simpleMovingAverage ->
                simpleMovingAverage.calculateIndicator(
                    lastAnalysisData.simpleMovingAverages,
                    currentAnalysisData.simpleMovingAverages
                )
            }
        currentAnalysisData.exponentialMovingAverages
            .values
            .forEach { exponentialMovingAverage ->
                exponentialMovingAverage.calculateIndicator(
                    lastAnalysisData.exponentialMovingAverages,
                    currentAnalysisData.exponentialMovingAverages
                )
            }

        analysisData = currentAnalysisData
        timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
    }
}
