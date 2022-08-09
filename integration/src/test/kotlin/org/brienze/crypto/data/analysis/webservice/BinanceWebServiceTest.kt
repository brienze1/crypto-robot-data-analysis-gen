package org.brienze.crypto.data.analysis.webservice

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.brienze.crypto.data.analysis.dto.BinanceCandleListResponseDto
import org.brienze.crypto.data.analysis.enums.Interval
import org.brienze.crypto.data.analysis.enums.Symbol
import org.brienze.crypto.data.analysis.exception.BinanceNullResponseBodyException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import kotlin.properties.Delegates

@ExtendWith(SpringExtension::class)
class BinanceWebServiceTest {

    @InjectMockKs
    private lateinit var binanceWebService: BinanceWebService

    @MockK
    private lateinit var restTemplate: RestTemplate

    private lateinit var binanceUrl: String
    private var quantity by Delegates.notNull<Int>()
    private lateinit var interval: Interval
    private lateinit var symbol: Symbol
    private lateinit var responseValue: Array<Array<String>>
    private lateinit var response: ResponseEntity<Array<Array<String>>>

    @BeforeEach
    fun init() {
        binanceUrl = "https://binanceUrl.com"
        ReflectionTestUtils.setField(binanceWebService, "binanceUrl", binanceUrl)

        quantity = 1
        interval = Interval.ONE_DAY
        symbol = Symbol.BTCUSDT
        responseValue = arrayOf(
            arrayOf(
                "1660003200000",
                "23810.98000000",
                "23869.96000000",
                "23727.95000000",
                "23799.02000000",
                "6484.77027000",
                "1660089599999",
                "154394059.47447220",
                "307058",
                "3318.93405000",
                "79021911.90514760",
                "0"
            )
        )
        response = ResponseEntity.ok(responseValue)
    }

    @Test
    fun getLastCandlesByIntervalSuccessTest() {
        every {
            restTemplate.exchange(
                UriComponentsBuilder
                    .fromHttpUrl(binanceUrl)
                    .queryParam("limit", quantity)
                    .queryParam("interval", interval.value)
                    .queryParam("symbol", symbol)
                    .toUriString(),
                HttpMethod.GET,
                HttpEntity<Any>(null, null),
                Array<Array<String>>::class.java
            )
        }.returns(response)

        val binanceCandleListResponse = binanceWebService.getLastCandlesByInterval(quantity, interval, symbol)

        verify(exactly = 1) { restTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(binanceUrl)
                .queryParam("limit", quantity)
                .queryParam("interval", interval.value)
                .queryParam("symbol", symbol)
                .toUriString(),
            HttpMethod.GET,
            HttpEntity<Any>(null, null),
            Array<Array<String>>::class.java
        ) }

        val expectedBinanceCandleListResponse =
            BinanceCandleListResponseDto(response.body!!, symbol).parseToCandleList()
                .sortedByDescending { candle -> candle.closeTime }

        Assertions.assertThat(binanceCandleListResponse).usingRecursiveComparison()
            .isEqualTo(expectedBinanceCandleListResponse)
    }

    @Test
    fun getLastCandlesByIntervalNullResponseTest() {
        response = ResponseEntity.ok(null)

        every {
            restTemplate.exchange(
                UriComponentsBuilder
                    .fromHttpUrl(binanceUrl)
                    .queryParam("limit", quantity)
                    .queryParam("interval", interval.value)
                    .queryParam("symbol", symbol)
                    .toUriString(),
                HttpMethod.GET,
                HttpEntity<Any>(null, null),
                Array<Array<String>>::class.java
            )
        }.returns(response)

        Assertions.assertThatThrownBy { binanceWebService.getLastCandlesByInterval(quantity, interval, symbol) }.isExactlyInstanceOf(
            BinanceNullResponseBodyException::class.java
        ).hasMessage("Response body from binance is null")

        verify(exactly = 1) { restTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(binanceUrl)
                .queryParam("limit", quantity)
                .queryParam("interval", interval.value)
                .queryParam("symbol", symbol)
                .toUriString(),
            HttpMethod.GET,
            HttpEntity<Any>(null, null),
            Array<Array<String>>::class.java
        ) }
    }

}