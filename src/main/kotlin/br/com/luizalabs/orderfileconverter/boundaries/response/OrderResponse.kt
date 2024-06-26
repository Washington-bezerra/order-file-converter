package br.com.luizalabs.orderfileconverter.boundaries.response

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class OrderConverterResponse(
    @JsonAlias("user_id")
    val userId: Int,
    val name: String,
    val orders: List<OrderResponse>
)

data class OrderResponse(
    @JsonAlias("order_id")
    val orderId: Int,
    val total: BigDecimal,
    val date: String,
    val products: List<ProductResponse>
)

data class ProductResponse(
    @JsonAlias("product_id")
    val productId: Int,
    val value: BigDecimal
)