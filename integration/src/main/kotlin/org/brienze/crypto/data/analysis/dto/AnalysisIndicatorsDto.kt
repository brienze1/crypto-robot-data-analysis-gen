package org.brienze.crypto.data.analysis.dto

import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.model.AnalysisIndicators

class AnalysisIndicatorsDto(
    analysisIndicators: AnalysisIndicators
) {

    val interval: Interval = analysisIndicators.interval
    val analysisData: AnalysisDataDto = AnalysisDataDto(analysisIndicators.analysisData)
    val timestamp: String = analysisIndicators.timestamp
}
