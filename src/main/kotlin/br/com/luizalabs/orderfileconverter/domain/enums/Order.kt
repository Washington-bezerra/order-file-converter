package br.com.luizalabs.orderfileconverter.domain.enums

import java.math.BigDecimal

data class Order(
    val user: Int,
    val name: String,
    val orderId: Int,
    val productId: Int,
    val valor: BigDecimal,
    val date: String
)