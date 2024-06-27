package br.com.luizalabs.orderfileconverter.domain.service

import br.com.luizalabs.orderfileconverter.infra.exception.UnprocessableEntityException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OrderFileLineValidatorServiceTest {

    val orderFileLineValidatorService: OrderFileLineValidatorService = OrderFileLineValidatorService()

    @Test
    fun `should return true when line size is exactly 95`() {
        val line = "0000000001                                      Zarelli00000001230000000111      512.2420211201"

        val result = orderFileLineValidatorService.validate(line)

        assertTrue(result)
    }


    @Test
    fun `should throw UnprocessableEntityException when line size is less than 95`() {
        val line = "0000000001                                     Zarelli00000001230000000111      512.2420211201"

        val exception = assertThrows<UnprocessableEntityException> {
            orderFileLineValidatorService.validate(line)
        }

        assertEquals("The size of all rows must be 95", exception.message)

    }


    @Test
    fun `should return true when line size is greater than 95`() {
        val line = "0000000001                                      Zarelli00000001230000000111      512.2420211201 "

        val exception = assertThrows<UnprocessableEntityException> {
            orderFileLineValidatorService.validate(line)
        }

        assertEquals("The size of all rows must be 95", exception.message)
    }
}