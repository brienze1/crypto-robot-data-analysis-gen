package org.brienze.crypto.data.analysis.dto

import org.brienze.crypto.data.analysis.enums.Indicator
import org.brienze.crypto.data.analysis.enums.Period
import java.math.BigDecimal

class MovingAverageDto(
    val period: Period,
    val value: BigDecimal,
    val indicator: Indicator
)