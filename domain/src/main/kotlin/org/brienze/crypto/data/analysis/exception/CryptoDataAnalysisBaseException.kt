package org.brienze.crypto.data.analysis.exception

abstract class CryptoDataAnalysisBaseException(
    val internalMessage: String,
    externalMessage: String
) : RuntimeException(externalMessage)
