package org.brienze.crypto.data.analysis.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class AwsBeanConfig {

    @Value("\${aws.local.config.url:}")
    private lateinit var awsUrl: String

    @Bean
    @Profile("localstack | local")
    fun localStackAmazonSQSAsyncClient(): AmazonSNS {
        return AmazonSNSClient.builder()
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(awsUrl, "sa-east-1")
            )
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials("default_access_key", "default_secret_key")
                )
            )
            .build()
    }

    @Bean
    @Profile("default")
    fun amazonSQSAsyncClient(): AmazonSNS {
        return AmazonSNSClient.builder()
            .build()
    }

}