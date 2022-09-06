package org.brienze.crypto.data.analysis.handler

import com.google.gson.Gson
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.brienze.crypto.data.analysis.dto.ErrorResponseDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@ExtendWith(MockKExtension::class)
class ErrorHandlerTest {

    @InjectMockKs
    private lateinit var errorHandler: ErrorHandler

    private val outContent = ByteArrayOutputStream()
    private val originalOut = System.out
    private val mapper = Gson()

    @BeforeEach
    fun init() {
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun finish() {
        System.setOut(originalOut)
    }

    @Test
    fun afterThrowingTest() {
        val error = RuntimeException("error")

        errorHandler.afterThrowing(error)

        Assertions.assertTrue(outContent.toString().contains(mapper.toJson(ErrorResponseDto(error))))
    }
}
