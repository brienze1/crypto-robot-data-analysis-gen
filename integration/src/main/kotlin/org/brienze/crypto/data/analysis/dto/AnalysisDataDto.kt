package org.brienze.crypto.data.analysis.dto

import org.brienze.crypto.data.analysis.model.AnalysisData

class AnalysisDataDto(
    analysisData: AnalysisData
) {

    val simpleMovingAverages: List<MovingAverageDto> = analysisData.simpleMovingAverages
        .values
        .sortedBy { it.period }
        .map { MovingAverageDto(it.period.value, it.value, it.indicator) }
        .toList()

    val exponentialMovingAverages: List<MovingAverageDto> = analysisData.exponentialMovingAverages
        .values
        .sortedBy { it.period }
        .map { MovingAverageDto(it.period.value, it.value, it.indicator) }
        .toList()
}
