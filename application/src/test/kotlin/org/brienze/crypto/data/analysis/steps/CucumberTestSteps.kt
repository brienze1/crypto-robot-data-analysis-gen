package org.brienze.crypto.data.analysis.steps

import com.amazonaws.services.sns.AmazonSNS
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import org.brienze.crypto.data.analysis.Application
import org.brienze.crypto.data.analysis.mock.BinanceApiMock
import org.brienze.crypto.data.analysis.scheduler.UpdateAnalysisScheduler
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import java.io.InputStream

@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(classes = [Application::class])
class CucumberTestSteps {

    @Autowired
    private lateinit var binanceApiMock: BinanceApiMock

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var updateAnalysisScheduler: UpdateAnalysisScheduler

    @Autowired
    private lateinit var sns: AmazonSNS

    @Given("the binance-api returns the data contained in {string} file from it's api:")
    fun theBinanceApiReturnsTheDataContainedInFileFromItSApi(filePath: String) {
        binanceApiMock.responseJson = readJsonFromFile(filePath)
    }

    @Given("the scheduled function was called")
    fun theApplicationIsUpAndRunning() {
        updateAnalysisScheduler.schedule1mIntervalData()
    }

    @When("the data is sent via sns topic")
    fun iTypeInTheCommandLines() {
        Mockito.verify(sns, Mockito.times(1)).publish(Mockito.any())
    }

    @Then("the data sent should contain the following values")
    fun theStdoutShouldReturnTheFollowingValues() {

    }

    private fun readJsonFromFile(filePath: String): String {
        val inputStream: InputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(filePath) as InputStream
        val jsonNode: JsonNode = objectMapper.readValue(inputStream, JsonNode::class.java)
        return objectMapper.writeValueAsString(jsonNode)
    }

}