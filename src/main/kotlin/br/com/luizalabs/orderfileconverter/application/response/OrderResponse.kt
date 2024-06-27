package br.com.luizalabs.orderfileconverter.application.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class OrderConverterResponse(
    @JsonProperty("user_id")
    val userId: Int,
    val name: String,
    val orders: List<OrderResponse>
)

data class OrderResponse(
    @JsonProperty("order_id")
    val orderId: Int,
    val total: BigDecimal,
    val date: String,
    val products: List<ProductResponse>
)

data class ProductResponse(
    @JsonProperty("product_id")
    val productId: Int,
    val value: BigDecimal
)