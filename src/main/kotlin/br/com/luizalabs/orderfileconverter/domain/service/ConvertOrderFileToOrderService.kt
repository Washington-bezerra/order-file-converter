package br.com.luizalabs.orderfileconverter.domain.service

import br.com.luizalabs.orderfileconverter.domain.enums.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal
import java.text.SimpleDateFormat

@Service
class ConvertOrderFileToOrderService {

    @Autowired
    lateinit var orderFileLineValidatorService: OrderFileLineValidatorService

    operator fun invoke (input: MultipartFile): MutableMap<Int, MutableList<Order>> {
        val ordersGrouped = mutableMapOf<Int, MutableList<Order>>()

        input.inputStream.reader().forEachLine { line ->
            orderFileLineValidatorService.validate(line)

            val order = Order(
                line.substring(0, 10).trim().toInt(),
                line.substring(10, 55).trim(),
                line.substring(55, 65).trim().toInt(),
                line.substring(65, 75).trim().toInt(),
                BigDecimal(line.substring(75, 87).trim()),
                SimpleDateFormat("yyyy-MM-dd").format(SimpleDateFormat("yyyyMMdd").parse(line.substring(87, 95).trim()))
            )
            ordersGrouped.getOrPut(order.user) { mutableListOf() }.add(order)
        }

        return ordersGrouped
    }
}