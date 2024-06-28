package br.com.luizalabs.orderfileconverter.domain.service

import br.com.luizalabs.orderfileconverter.domain.enums.Order
import br.com.luizalabs.orderfileconverter.infra.exception.UnprocessableEntityException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
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
        val multipartFile = buildMockMultipartFile()

        every { orderFileLineValidatorService.validate(any(String::class)) } returns true

        val result = service(multipartFile)

        val expected = buildOrderMap()

        verify (exactly = 4){ orderFileLineValidatorService.validate(any(String::class)) }
        assertEquals(result, expected)
    }

    @Test
    fun `should not convert when any line is invalid`() {
        val multipartFile = buildMockMultipartFile()

        every { orderFileLineValidatorService.validate(any(String::class)) } returns true andThenThrows UnprocessableEntityException("The size of all rows must be 95")

        try {
            service(multipartFile)
        }catch (e: UnprocessableEntityException){
            verify (exactly = 2){ orderFileLineValidatorService.validate(any(String::class)) }
        }
    }

    private fun buildMockMultipartFile(): MockMultipartFile {
        val path = Paths.get("src/test/kotlin/br/com/luizalabs/orderfileconverter/data/data_menor.txt")
        val content = Files.readAllBytes(path)
        val multipartFile = MockMultipartFile("file", "data_menor.txt", "text/plain", content)
        return multipartFile
    }

    private fun buildOrderMap(): MutableMap<Int, MutableList<Order>> {
        val orderMap = mutableMapOf<Int, MutableList<Order>>()

        val order = Order(
            "0000000002".toInt(),
            "                                     Medeiros".trim(),
            "0000012345".toInt(),
            "0000000111".trim().toInt(),
            BigDecimal("      256.24".trim()),
            SimpleDateFormat("yyyy-MM-dd").format(SimpleDateFormat("yyyyMMdd").parse("20201201".trim()))
        )
        orderMap.getOrPut(order.user) { mutableListOf() }.add(order)

        val order1 = Order(
            "0000000002".toInt(),
            "                                     Medeiros".trim(),
            "0000012345".toInt(),
            "0000000122".trim().toInt(),
            BigDecimal("      256.24".trim()),
            SimpleDateFormat("yyyy-MM-dd").format(SimpleDateFormat("yyyyMMdd").parse("20201201".trim()))
        )
        orderMap.getOrPut(order1.user) { mutableListOf() }.add(order1)

        val order2 = Order(
            "0000000001".toInt(),
            "                                      Zarelli".trim(),
            "0000000123".toInt(),
            "0000000111".trim().toInt(),
            BigDecimal("      512.24".trim()),
            SimpleDateFormat("yyyy-MM-dd").format(SimpleDateFormat("yyyyMMdd").parse("20211201".trim()))
        )
        orderMap.getOrPut(order2.user) { mutableListOf() }.add(order2)

        val order3 = Order(
            "0000000001".toInt(),
            "                                      Zarelli".trim(),
            "0000000123".toInt(),
            "0000000122".trim().toInt(),
            BigDecimal("      512.24".trim()),
            SimpleDateFormat("yyyy-MM-dd").format(SimpleDateFormat("yyyyMMdd").parse("20211201".trim()))
        )
        orderMap.getOrPut(order3.user) { mutableListOf() }.add(order3)

        return orderMap
    }

}