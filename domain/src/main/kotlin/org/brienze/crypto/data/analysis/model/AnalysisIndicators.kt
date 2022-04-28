package org.brienze.crypto.data.analysis.model

import org.brienze.crypto.data.analysis.enums.Interval

class AnalysisIndicators(
    val interval: Interval,
    currentAnalysisData: AnalysisData,
    lastAnalysisData: AnalysisData
) {

    private val analysisData: AnalysisData

    init {
        currentAnalysisData.simpleMovingAverages
            .values
            .forEach { simpleMovingAverage -> simpleMovingAverage.calculateIndicator(lastAnalysisData.simpleMovingAverages ,currentAnalysisData.simpleMovingAverages) }
        currentAnalysisData.exponentialMovingAverages
            .values
            .forEach { exponentialMovingAverage -> exponentialMovingAverage.calculateIndicator(lastAnalysisData.exponentialMovingAverages, currentAnalysisData.exponentialMovingAverages) }

        analysisData = currentAnalysisData
    }

}