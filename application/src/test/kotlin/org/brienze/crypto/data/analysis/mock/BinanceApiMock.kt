package org.brienze.crypto.data.analysis.mock

import org.brienze.crypto.data.analysis.enums.Symbol
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/mock/api/v3")
class BinanceApiMock {

    lateinit var responseJson: String

    @GetMapping("/klines")
    fun getKlines(
        @RequestParam("limit") limit: Int,
        @RequestParam("interval") interval: String,
        @RequestParam("symbol") symbol: Symbol
    ): String {
        return responseJson
    }

}