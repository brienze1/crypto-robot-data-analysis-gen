package org.brienze.crypto.data.analysis.scheduler

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.usecase.UpdateAnalysisUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class UpdateAnalysisSchedulerTest {

    @InjectMockKs
    private lateinit var updateAnalysisScheduler: UpdateAnalysisScheduler

    @MockK
    private lateinit var updateAnalysisUseCase: UpdateAnalysisUseCase

    @BeforeEach
    fun init() {
        every { updateAnalysisUseCase.update(any()) } returns Unit
    }

    @Test
    fun schedule1mIntervalDataTest() {
        updateAnalysisScheduler.schedule1mIntervalData()

        verify(exactly = 1) { updateAnalysisUseCase.update(Interval.ONE_MINUTE) }
    }

    @Test
    fun schedule5mIntervalDataTest() {
        updateAnalysisScheduler.schedule5mIntervalData()

        verify(exactly = 1) { updateAnalysisUseCase.update(Interval.FIVE_MINUTES) }
    }

    @Test
    fun schedule15mIntervalDataTest() {
        updateAnalysisScheduler.schedule15mIntervalData()

        verify(exactly = 1) { updateAnalysisUseCase.update(Interval.FIFTEEN_MINUTES) }
    }

    @Test
    fun schedule30mIntervalDataTest() {
        updateAnalysisScheduler.schedule30mIntervalData()

        verify(exactly = 1) { updateAnalysisUseCase.update(Interval.THIRTY_MINUTES) }
    }

    @Test
    fun schedule1hIntervalDataTest() {
        updateAnalysisScheduler.schedule1hIntervalData()

        verify(exactly = 1) { updateAnalysisUseCase.update(Interval.ONE_HOUR) }
    }

    @Test
    fun schedule6hIntervalDataTest() {
        updateAnalysisScheduler.schedule6hIntervalData()

        verify(exactly = 1) { updateAnalysisUseCase.update(Interval.SIX_HOURS) }
    }

    @Test
    fun schedule1dIntervalDataTest() {
        updateAnalysisScheduler.schedule1dIntervalData()

        verify(exactly = 1) { updateAnalysisUseCase.update(Interval.ONE_DAY) }
    }

}
