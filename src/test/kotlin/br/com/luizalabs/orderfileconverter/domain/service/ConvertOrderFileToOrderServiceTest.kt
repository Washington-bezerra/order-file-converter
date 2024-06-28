package br.com.luizalabs.orderfileconverter.domain.service

import br.com.luizalabs.orderfileconverter.builder.MultipartfileBuilder
import br.com.luizalabs.orderfileconverter.builder.OrderMapBuilder
import br.com.luizalabs.orderfileconverter.domain.enums.Order
import br.com.luizalabs.orderfileconverter.infra.exception.UnprocessableEntityException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConvertOrderFileToOrderServiceTest {

    @MockK
    lateinit var orderFileLineValidatorService: OrderFileLineValidatorService

    @InjectMockKs
    private lateinit var service: ConvertOrderFileToOrderService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `should convert order file when file is valid`() {
        val multipartFile = MultipartfileBuilder.build()

        every { orderFileLineValidatorService.validate(any(String::class)) } returns true

        val result = service(multipartFile)

        val expected = OrderMapBuilder.build()

        verify (exactly = 4){ orderFileLineValidatorService.validate(any(String::class)) }
        assertEquals(result, expected)
    }

    @Test
    fun `should not convert when any line is invalid`() {
        val multipartFile = MultipartfileBuilder.build()

        every { orderFileLineValidatorService.validate(any(String::class)) } returns true andThenThrows UnprocessableEntityException("The size of all rows must be 95")

        try {
            service(multipartFile)
        }catch (e: UnprocessableEntityException){
            verify (exactly = 2){ orderFileLineValidatorService.validate(any(String::class)) }
        }
    }
}