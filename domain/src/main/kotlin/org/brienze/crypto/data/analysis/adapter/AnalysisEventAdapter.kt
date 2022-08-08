package org.brienze.crypto.data.analysis.adapter

import org.brienze.crypto.data.analysis.model.AnalysisIndicators

interface AnalysisEventAdapter {

    fun sendEvent(analysisIndicators: AnalysisIndicators)

}