package br.com.luizalabs.orderfileconverter.domain

import java.math.BigDecimal
import java.util.Date

data class Order(
    val user: Int,
    val name: String,
    val orderId: Int,
    val productId: Int,
    val valor: BigDecimal,
    val date: Date
)