package org.brienze.crypto.data.analysis.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.brienze.crypto.data.analysis.dto.ErrorResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
class ErrorHandler(
    @Autowired
    val objectMapper: ObjectMapper
) {

    @AfterThrowing(
        pointcut = "execution(* org.brienze.crypto.data.analysis.scheduler.Scheduler.*(..))",
        throwing = "ex"
    )
    fun afterThrowing(ex: Exception) {
        println("\n-------- Error Message Start ------\n")
        println(objectMapper.writeValueAsString(ErrorResponseDto(ex)))
        println("\n-------- Error Message End --------\n")
    }
}