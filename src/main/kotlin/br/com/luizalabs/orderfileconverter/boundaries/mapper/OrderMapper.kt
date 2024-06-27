package br.com.luizalabs.orderfileconverter.boundaries.mapper

import br.com.luizalabs.orderfileconverter.application.response.OrderConverterResponse
import br.com.luizalabs.orderfileconverter.application.response.OrderResponse
import br.com.luizalabs.orderfileconverter.application.response.ProductResponse
import br.com.luizalabs.orderfileconverter.domain.enums.Order
import org.springframework.stereotype.Component

@Component
class OrderMapper {

    fun toOrderConverterResponses(orders: MutableMap<Int, MutableList<Order>>): ArrayList<OrderConverterResponse> {
        val orderConverterResponses = ArrayList<OrderConverterResponse>()

        orders.forEach { (user, orders) ->

            val ordersGrouped = orders.groupBy { it.orderId }
            val orderResponse = ArrayList<OrderResponse>()

            ordersGrouped.forEach{ order ->
                val products = ArrayList<ProductResponse>()
                order.value.forEach {
                    products.add(
                        ProductResponse(
                            it.productId,
                            it.valor
                        )
                    )
                }

                orderResponse.add(
                    OrderResponse(
                        order.key,
                        order.value.sumOf { it.valor },
                        order.value[0].date,
                        products
                    )
                )
            }

            orderConverterResponses.add(
                OrderConverterResponse(
                    user,
                    orders[0].name,
                    orderResponse
                )
            )
        }

        return orderConverterResponses
    }
}