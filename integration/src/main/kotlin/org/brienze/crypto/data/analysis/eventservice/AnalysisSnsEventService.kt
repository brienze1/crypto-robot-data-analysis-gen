package org.brienze.crypto.data.analysis.eventservice

import org.brienze.crypto.data.analysis.adapter.AnalysisEventService
import org.brienze.crypto.data.analysis.model.AnalysisIndicators
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AnalysisSnsEventService: AnalysisEventService, BasicEventService() {

    @Value("\${event.analysis.topic.arn}")
    private lateinit var analysisTopicArn: String

    override fun sendEvent(analysisIndicators: AnalysisIndicators) {
        sendEvent(analysisIndicators, analysisTopicArn)
    }

}