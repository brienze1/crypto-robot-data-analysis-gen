package org.brienze.crypto.data.analysis.webservice

import org.brienze.crypto.data.analysis.adapter.CandleServiceAdapter
import org.brienze.crypto.data.analysis.dto.BinanceCandleListResponseDto
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.enums.Symbol
import org.brienze.crypto.data.analysis.exception.BinanceNullResponseBodyException
import org.brienze.crypto.data.analysis.model.Candle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*


@Component
class BinanceWebService : CandleServiceAdapter {

    @Autowired
    lateinit var restTemplate: RestTemplate

    override fun getLastCandlesByInterval(quantity: Int, interval: Interval, symbol: Symbol): List<Candle> {
        val request = HttpEntity<Any>(null, null)

        val response: ResponseEntity<Array<Array<String>>> = restTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl("https://api.binance.com")
                .path("/api/v3/klines")
                .queryParam("limit", quantity)
                .queryParam("interval", interval.value)
                .queryParam("symbol", symbol)
                .toUriString(),
            HttpMethod.GET,
            request,
            Array<Array<String>>::class.java
        )

        if(response.body == null){
            throw BinanceNullResponseBodyException()
        }

        val binanceCandleListResponseDto = BinanceCandleListResponseDto(response.body!!, symbol)

        return binanceCandleListResponseDto.parseToCandleList().sortedByDescending { candle -> candle.closeTime }
    }

}