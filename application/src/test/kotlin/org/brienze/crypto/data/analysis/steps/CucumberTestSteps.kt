package org.brienze.crypto.data.analysis.steps

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import com.google.gson.Gson
import com.google.gson.JsonElement
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import org.brienze.crypto.data.analysis.Application
import org.brienze.crypto.data.analysis.dto.AnalysisIndicatorsDto
import org.brienze.crypto.data.analysis.dto.MovingAverageDto
import org.brienze.crypto.data.analysis.mock.BinanceApiMock
import org.brienze.crypto.data.analysis.scheduler.UpdateAnalysisScheduler
import org.brienze.crypto.data.analysis.webservice.BinanceWebService
import org.junit.jupiter.api.Assertions
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.util.ReflectionTestUtils
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import kotlin.reflect.full.memberProperties

@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CucumberTestSteps {

    @LocalServerPort
    protected var serverPort: Int = 0

    @Autowired
    private lateinit var binanceApiMock: BinanceApiMock

    @Autowired
    private lateinit var mapper: Gson

    @Autowired
    private lateinit var updateAnalysisScheduler: UpdateAnalysisScheduler

    @Autowired
    private lateinit var sns: AmazonSNS

    @Autowired
    private lateinit var binanceWebService: BinanceWebService

    @Captor
    private var publishRequestCaptor = ArgumentCaptor.forClass(PublishRequest::class.java)

    private lateinit var analysisIndicatorsSent: AnalysisIndicatorsDto

    @Before
    fun init() {
        ReflectionTestUtils.setField(binanceWebService, "binanceUrl", "http://localhost:$serverPort/mock/api/v3/klines")
    }

    @Given("the binance-api returns the data contained in {string} file from it's api:")
    fun theBinanceApiReturnsTheDataContainedInFileFromItSApi(filePath: String) {
        binanceApiMock.responseJson = readJsonFromFile(filePath)
    }

    @Given("the scheduled function was called")
    fun theScheduledFunctionWasCalled() {
        updateAnalysisScheduler.schedule1mIntervalData()
    }

    @When("the data is sent via sns topic")
    fun theDataIsSentViaSnsTopic() {
        Mockito.verify(sns, Mockito.times(1)).publish(publishRequestCaptor.capture())
    }

    @Then("the data sent should be equal {string} file")
    fun theDataSentShouldBeEqualFile(filePath: String) {
        analysisIndicatorsSent = mapper.fromJson(publishRequestCaptor.value.message, AnalysisIndicatorsDto::class.java)
        val analysisIndicatorsExpected: AnalysisIndicatorsDto =
            mapper.fromJson(readJsonFromFile(filePath), AnalysisIndicatorsDto::class.java)

        Assertions.assertNotNull(analysisIndicatorsSent)
        Assertions.assertNotNull(analysisIndicatorsSent.analysisData)
        Assertions.assertNotNull(analysisIndicatorsSent.analysisData.exponentialMovingAverages)
        Assertions.assertNotNull(analysisIndicatorsSent.analysisData.simpleMovingAverages)
        Assertions.assertFalse(analysisIndicatorsSent.analysisData.exponentialMovingAverages.isEmpty())
        Assertions.assertFalse(analysisIndicatorsSent.analysisData.simpleMovingAverages.isEmpty())
        Assertions.assertEquals(analysisIndicatorsExpected.interval, analysisIndicatorsSent.interval)

        for (movingAverage: MovingAverageDto in analysisIndicatorsSent.analysisData.simpleMovingAverages) {
            for (movingAverageExpected: MovingAverageDto in analysisIndicatorsExpected.analysisData.simpleMovingAverages) {
                if (movingAverageExpected.period == movingAverage.period) {
                    Assertions.assertEquals(movingAverageExpected.indicator, movingAverage.indicator)
                    Assertions.assertEquals(movingAverageExpected.value, movingAverage.value)
                }
            }
        }

        for (movingAverage: MovingAverageDto in analysisIndicatorsSent.analysisData.exponentialMovingAverages) {
            for (movingAverageExpected: MovingAverageDto in analysisIndicatorsExpected.analysisData.exponentialMovingAverages) {
                if (movingAverageExpected.period == movingAverage.period) {
                    Assertions.assertEquals(movingAverageExpected.indicator, movingAverage.indicator)
                    Assertions.assertEquals(movingAverageExpected.value, movingAverage.value)
                }
            }
        }
    }

    @When("the field {string} should not be null")
    fun theFieldShouldNotBeNull(field: String) {
        val value = AnalysisIndicatorsDto::class.memberProperties
            .first { it.name == field }
            .getter(analysisIndicatorsSent) as String

        Assertions.assertNotNull(value)
        Assertions.assertFalse(value.trim().isEmpty())
    }

    private fun readJsonFromFile(filePath: String): String {
        val reader: Reader =
            InputStreamReader(Thread.currentThread().contextClassLoader.getResourceAsStream(filePath) as InputStream)
        val jsonNode = mapper.fromJson(reader, JsonElement::class.java)
        return mapper.toJson(jsonNode)
    }
}
