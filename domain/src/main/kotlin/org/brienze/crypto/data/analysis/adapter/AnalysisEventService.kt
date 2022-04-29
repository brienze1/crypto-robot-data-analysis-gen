package org.brienze.crypto.data.analysis.adapter

import org.brienze.crypto.data.analysis.model.AnalysisIndicators
import org.springframework.stereotype.Component

@Component
interface AnalysisEventService {

    fun sendEvent(analysisIndicators: AnalysisIndicators)

}