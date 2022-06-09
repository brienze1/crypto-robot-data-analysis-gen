package org.brienze.crypto.data.analysis.steps

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import org.brienze.crypto.data.analysis.Application
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(classes = [Application::class])
class CucumberTestSteps {

    @Given("the following operation:")
    fun theFollowingOperation(test: String) {

    }

    @When("I type in the command lines")
    fun iTypeInTheCommandLines() {

    }

    @Then("the stdout should return the following values:")
    fun theStdoutShouldReturnTheFollowingValues(test: String) {

    }

}