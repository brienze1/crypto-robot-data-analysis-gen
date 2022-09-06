package org.brienze.crypto.data.analysis.eventservice

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import com.amazonaws.services.sns.model.PublishResult
import com.google.gson.Gson
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.brienze.crypto.data.analysis.dto.AnalysisIndicatorsDto
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.model.AnalysisData
import org.brienze.crypto.data.analysis.model.AnalysisIndicators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.util.ReflectionTestUtils

@ExtendWith(MockKExtension::class)
class AnalysisSnsEventServiceTest {

    @InjectMockKs
    private lateinit var analysisSnsEventService: AnalysisSnsEventService

    @MockK
    private lateinit var sns: AmazonSNS

    private var mapper: Gson = Gson()
    private lateinit var analysisTopicArn: String
    private lateinit var analysisIndicators: AnalysisIndicators

    @BeforeEach
    fun init() {
        analysisTopicArn = "analysisTopicArn"

        ReflectionTestUtils.setField(analysisSnsEventService, "analysisTopicArn", analysisTopicArn)

        analysisIndicators = AnalysisIndicators(Interval.SIX_HOURS, AnalysisData(listOf()), AnalysisData(listOf()))
    }

    @Test
    fun sendEventTest() {
        every {
            sns.publish(
                PublishRequest().withMessage(mapper.toJson(AnalysisIndicatorsDto(analysisIndicators)))
                    .withTopicArn(analysisTopicArn)
            )
        }.returns(PublishResult())

        analysisSnsEventService.sendEvent(analysisIndicators)

        verify(exactly = 1) {
            sns.publish(
                PublishRequest().withMessage(mapper.toJson(AnalysisIndicatorsDto(analysisIndicators)))
                    .withTopicArn(analysisTopicArn)
            )
        }
    }
}
