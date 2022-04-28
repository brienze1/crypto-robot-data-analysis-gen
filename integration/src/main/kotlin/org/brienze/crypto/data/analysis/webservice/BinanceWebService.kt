package org.brienze.crypto.data.analysis.webservice

import org.brienze.crypto.data.analysis.adapter.CandleServiceAdapter
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.model.Candle
import org.springframework.stereotype.Component

@Component
class BinanceWebService: CandleServiceAdapter {

    override fun getLastCandlesByInterval(quantity: Int, interval: Interval): List<Candle> {
        return listOf()
    }

}