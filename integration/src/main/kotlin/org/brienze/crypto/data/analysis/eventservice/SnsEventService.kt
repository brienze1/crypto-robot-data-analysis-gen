package org.brienze.crypto.data.analysis.eventservice

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired

abstract class SnsEventService {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var sns: AmazonSNS

    protected fun sendEvent(messageObject: Any, topic: String) {
        println(objectMapper.writeValueAsString(messageObject))
        println(topic)

        val result = sns.publish(PublishRequest()
                .withMessage(objectMapper.writeValueAsString(messageObject))
                .withTopicArn(topic))

        println(objectMapper.writeValueAsString(result))
    }

}