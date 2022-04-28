package org.brienze.crypto.data.analysis.dto

class ErrorResponseDto(
    ex: Exception,
) {

    private val message: String
    private val exception: String

    init {
        message = ex.message.toString()
        exception = ex.javaClass.toString()
    }

}
