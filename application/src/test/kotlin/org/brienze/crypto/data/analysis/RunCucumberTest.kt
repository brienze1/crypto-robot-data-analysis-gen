package org.brienze.crypto.data.analysis

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features"],
    glue = ["org.brienze.crypto.data.analysis.steps"],
    plugin = ["pretty"]
)
class RunCucumberTest {
}