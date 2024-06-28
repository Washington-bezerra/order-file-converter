package br.com.luizalabs.orderfileconverter.application.usecase

import br.com.luizalabs.orderfileconverter.builder.MultipartfileBuilder
import br.com.luizalabs.orderfileconverter.builder.OrderMapBuilder
import br.com.luizalabs.orderfileconverter.domain.service.ConvertOrderFileToOrderService
import br.com.luizalabs.orderfileconverter.domain.service.GenerateSHA256HashService
import br.com.luizalabs.orderfileconverter.infra.entities.OrderFileLog
import br.com.luizalabs.orderfileconverter.infra.repositories.OrderFileLogRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional

class OrderFileConverterUseCaseTest {

    @MockK
    lateinit var convertOrderFileToOrderService: ConvertOrderFileToOrderService

    @MockK
    lateinit var generateSHA256HashService: GenerateSHA256HashService

    @MockK
    lateinit var orderFileLogRepository: OrderFileLogRepository

    @InjectMockKs
    lateinit var orderFileConverterUseCase: OrderFileConverterUseCase

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `should convert order file when file is valid`() {
        val multipartFile = MultipartfileBuilder.build()
        val orderMap = OrderMapBuilder.build()

        every { convertOrderFileToOrderService(multipartFile) } returns orderMap
        every { generateSHA256HashService(any()) } returns "HashTest"
        every { orderFileLogRepository.save(any()) } returns mockk<OrderFileLog>()

        val result = orderFileConverterUseCase(multipartFile, "userName")

        verify (exactly = 1){ convertOrderFileToOrderService(multipartFile) }
        verify (exactly = 1){ generateSHA256HashService(any()) }
        verify (exactly = 1){ orderFileLogRepository.save(any()) }
        assertEquals(result, orderMap)
    }

    @Test
    fun `should not convert order file when file is invalid`() {
        val multipartFile = MultipartfileBuilder.build()

        every { convertOrderFileToOrderService(multipartFile) } throws RuntimeException()
        every { orderFileLogRepository.save(any()) } returns mockk<OrderFileLog>()

        assertThrows<Exception> {
            orderFileConverterUseCase(multipartFile, "userName")
        }

        verify (exactly = 1){ convertOrderFileToOrderService(multipartFile) }
        verify (exactly = 0){ generateSHA256HashService(any()) }
        verify (exactly = 1){ orderFileLogRepository.save(any()) }

    }
}