package org.brienze.crypto.data.analysis.enums

import java.math.BigDecimal

enum class Factor(
    val value: BigDecimal
) {
    SMOOTHENING_FACTOR(BigDecimal(0.3))
}
