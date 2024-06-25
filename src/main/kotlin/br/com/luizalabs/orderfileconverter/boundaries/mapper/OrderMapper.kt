package br.com.luizalabs.orderfileconverter.boundaries.mapper

import br.com.luizalabs.orderfileconverter.boundaries.response.OrderConverterResponse
import br.com.luizalabs.orderfileconverter.boundaries.response.OrderResponse
import br.com.luizalabs.orderfileconverter.boundaries.response.ProductResponse
import br.com.luizalabs.orderfileconverter.domain.Order
import org.springframework.stereotype.Component

@Component
class OrderMapper {

    fun toOrderConverterResponses(orders: MutableMap<Int, MutableList<Order>>): ArrayList<OrderConverterResponse> {
        val orderConverterResponses = ArrayList<OrderConverterResponse>()

        orders.forEach { (user, orders) ->

            val ordersGrouped = orders.groupBy { it.orderId }
            val orderResponse = ArrayList<OrderResponse>()

            ordersGrouped.forEach{
                val products = ArrayList<ProductResponse>()
                it.value.forEach {
                    products.add(
                        ProductResponse(
                            it.productId,
                            it.valor
                        )
                    )
                }

                orderResponse.add(
                    OrderResponse(
                        it.key,
                        it.value.sumOf { it.valor },
                        it.value.get(0).date,
                        products
                    )
                )
            }

            orderConverterResponses.add(
                OrderConverterResponse(
                    user,
                    orders.get(0).name,
                    orderResponse
                )
            )
        }

        return orderConverterResponses
    }
}