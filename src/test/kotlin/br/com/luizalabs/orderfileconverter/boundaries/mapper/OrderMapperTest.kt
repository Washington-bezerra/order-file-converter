package br.com.luizalabs.orderfileconverter.boundaries.mapper

import br.com.luizalabs.orderfileconverter.application.response.OrderConverterResponse
import br.com.luizalabs.orderfileconverter.application.response.OrderResponse
import br.com.luizalabs.orderfileconverter.application.response.ProductResponse
import br.com.luizalabs.orderfileconverter.builder.OrderMapBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class OrderMapperTest {

    private val orderMapper = OrderMapper()

    @Test
    fun `toOrderConverterResponses should return correct responses`() {
        val orders = OrderMapBuilder.build()

        val result = orderMapper.toOrderConverterResponses(orders)

        val responses = listOf(
            OrderConverterResponse(
                userId = 2,
                name = "Medeiros",
                orders = listOf(
                    OrderResponse(
                        orderId = 12345,
                        total = 512.48.toBigDecimal(),
                        date = "2020-12-01",
                        products = listOf(
                            ProductResponse(productId = 111, value = 256.24.toBigDecimal()),
                            ProductResponse(productId = 122, value = 256.24.toBigDecimal())
                        )
                    )
                )
            ),
            OrderConverterResponse(
                userId = 1,
                name = "Zarelli",
                orders = listOf(
                    OrderResponse(
                        orderId = 123,
                        total = 1024.48.toBigDecimal(),
                        date = "2021-12-01",
                        products = listOf(
                            ProductResponse(productId = 111, value = 512.24.toBigDecimal()),
                            ProductResponse(productId = 122, value = 512.24.toBigDecimal())
                        )
                    )
                )
            )
        )

        assertEquals(responses, result)
    }
}