package org.brienze.crypto.data.analysis.adapter

import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.enums.Symbol
import org.brienze.crypto.data.analysis.model.Candle

interface CandleServiceAdapter {

    fun getLastCandlesByInterval(quantity: Int, interval: Interval, symbol: Symbol): List<Candle>
}
