package org.brienze.crypto.data.analysis.adapter

import org.brienze.crypto.data.analysis.model.AnalysisIndicators

interface AnalysisEventService {

    fun sendEvent(analysisIndicators: AnalysisIndicators)

}