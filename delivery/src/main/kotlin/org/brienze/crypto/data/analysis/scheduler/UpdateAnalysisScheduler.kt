package org.brienze.crypto.data.analysis.scheduler

import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.usecase.UpdateAnalysisUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class UpdateAnalysisScheduler {

    @Autowired
    private lateinit var updateAnalysisUseCase: UpdateAnalysisUseCase

    @Async
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    fun schedule1mIntervalData() {
        updateAnalysisUseCase.update(Interval.ONE_MINUTE)
    }

    @Async
    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    fun schedule5mIntervalData() {
        updateAnalysisUseCase.update(Interval.FIVE_MINUTES)
    }

    @Async
    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES)
    fun schedule15mIntervalData() {
        updateAnalysisUseCase.update(Interval.FIFTEEN_MINUTES)
    }

    @Async
    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
    fun schedule30mIntervalData() {
        updateAnalysisUseCase.update(Interval.THIRTY_MINUTES)
    }

    @Async
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    fun schedule1hIntervalData() {
        updateAnalysisUseCase.update(Interval.ONE_HOUR)
    }

    @Async
    @Scheduled(fixedRate = 6, timeUnit = TimeUnit.HOURS)
    fun schedule6hIntervalData() {
        updateAnalysisUseCase.update(Interval.SIX_HOURS)
    }

    @Async
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    fun schedule1dIntervalData() {
        updateAnalysisUseCase.update(Interval.ONE_DAY)
    }

}