package org.brienze.crypto.data.analysis.config

import com.amazonaws.services.sns.AmazonSNS
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("cucumber-test")
class AwsMockBeanConfig {

    @Bean
    @Profile("cucumber-test")
    fun amazonSQSAsyncClient(): AmazonSNS {
        return Mockito.mock(AmazonSNS::class.java)
    }

}