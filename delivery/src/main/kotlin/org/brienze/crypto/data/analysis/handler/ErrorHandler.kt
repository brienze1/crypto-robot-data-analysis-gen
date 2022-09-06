package org.brienze.crypto.data.analysis.handler

import com.google.gson.Gson
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.brienze.crypto.data.analysis.dto.ErrorResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
class ErrorHandler {

    @Autowired
    private lateinit var mapper: Gson

    @AfterThrowing(
        pointcut = "execution(* org.brienze.crypto.data.analysis.scheduler.UpdateAnalysisScheduler.*(..))",
        throwing = "ex"
    )
    fun afterThrowing(ex: Exception) {
        println("\n-------- Error Message Start ------\n")
        println(mapper.toJson(ErrorResponseDto(ex)))
        println("\n-------- Error Message End --------\n")
    }
}
