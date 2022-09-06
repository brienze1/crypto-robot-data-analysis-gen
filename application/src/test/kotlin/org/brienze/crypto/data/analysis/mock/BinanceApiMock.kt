package org.brienze.crypto.data.analysis.mock

import org.brienze.crypto.data.analysis.enums.Symbol
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mock/api/v3")
class BinanceApiMock {

    lateinit var responseJson: String

    @GetMapping("klines")
    fun getKlines(
        @RequestParam(required = false) limit: Int,
        @RequestParam(required = false) interval: String,
        @RequestParam(required = false) symbol: Symbol
    ): String {
        return responseJson
    }
}
