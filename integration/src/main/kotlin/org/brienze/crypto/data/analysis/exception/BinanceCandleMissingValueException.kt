package org.brienze.crypto.data.analysis.exception

class BinanceCandleMissingValueException(
    valueMissing: String,
    externalMessage: String = "The candle retrieved from the Binance API is missing some values"
) : CryptoDataAnalysisBaseException("Missing '$valueMissing' value.", externalMessage)
