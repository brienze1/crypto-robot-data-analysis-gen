package org.brienze.crypto.data.analysis.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@Profile("!cucumber-test")
class SchedulerConfig {
}