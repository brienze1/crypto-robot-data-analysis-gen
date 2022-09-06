package org.brienze.crypto.data.analysis.eventservice

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import com.google.gson.Gson
import org.brienze.crypto.data.analysis.adapter.AnalysisEventAdapter
import org.brienze.crypto.data.analysis.dto.AnalysisIndicatorsDto
import org.brienze.crypto.data.analysis.model.AnalysisIndicators
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AnalysisSnsEventService : AnalysisEventAdapter {

    @Value("\${event.analysis.topic.arn}")
    private lateinit var analysisTopicArn: String

    @Autowired
    private lateinit var mapper: Gson

    @Autowired
    private lateinit var sns: AmazonSNS

    override fun sendEvent(analysisIndicators: AnalysisIndicators) {
        // TODO add logger
        println(mapper.toJson(AnalysisIndicatorsDto(analysisIndicators)))
        println(analysisTopicArn)

        val result = sns.publish(
            PublishRequest()
                .withMessage(mapper.toJson(AnalysisIndicatorsDto(analysisIndicators)))
                .withTopicArn(analysisTopicArn)
        )

        println(mapper.toJson(result))
    }
}
