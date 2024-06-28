package br.com.luizalabs.orderfileconverter.builder

import br.com.luizalabs.orderfileconverter.domain.enums.Order
import java.math.BigDecimal
import java.text.SimpleDateFormat

class OrderMapBuilder {

    companion object {
        fun build(): MutableMap<Int, MutableList<Order>> {
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

}