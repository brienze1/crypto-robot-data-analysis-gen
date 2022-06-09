package org.brienze.crypto.data.analysis.eventservice

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.brienze.crypto.data.analysis.adapter.AnalysisEventService
import org.brienze.crypto.data.analysis.model.AnalysisIndicators
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AnalysisSnsEventService: AnalysisEventService {

    @Value("\${event.analysis.topic.arn}")
    private lateinit var analysisTopicArn: String

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var sns: AmazonSNS

    override fun sendEvent(analysisIndicators: AnalysisIndicators) {
        println(objectMapper.writeValueAsString(analysisIndicators))
        println(analysisTopicArn)

        val result = sns.publish(
            PublishRequest()
            .withMessage(objectMapper.writeValueAsString(analysisIndicators))
            .withTopicArn(analysisTopicArn))

        println(objectMapper.writeValueAsString(result))
    }

}