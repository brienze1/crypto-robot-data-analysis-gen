package org.brienze.crypto.data.analysis.dto

import org.brienze.crypto.data.analysis.enums.Indicator
import java.math.BigDecimal

class MovingAverageDto(
    val period: Int,
    val value: BigDecimal,
    val indicator: Indicator
)
