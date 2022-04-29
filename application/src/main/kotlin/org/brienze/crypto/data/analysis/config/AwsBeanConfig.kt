package org.brienze.crypto.data.analysis.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class AwsBeanConfig {

    @Bean
    @Profile("localstack", "dev")
    fun localStackAmazonSQSAsyncClient(): AmazonSNS {
        return AmazonSNSClient.builder()
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1")
            )
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials("default_access_key", "default_secret_key")
                )
            )
            .build()
    }

    @Bean
    @Profile("!localstack & !dev")
    fun amazonSQSAsyncClient(): AmazonSNS {
        return AmazonSNSClient.builder()
            .build()
    }

}