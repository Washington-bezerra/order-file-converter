package br.com.luizalabs.orderfileconverter.application.usecases

import br.com.luizalabs.orderfileconverter.domain.Order
import br.com.luizalabs.orderfileconverter.domain.service.ConvertOrderFileLineToOrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class OrderFileConverterUseCase {

    @Autowired
    lateinit var convertOrderFileLineToOrderService: ConvertOrderFileLineToOrderService

    operator fun invoke(orderFile: MultipartFile): MutableMap<Int, MutableList<Order>> {

        val input = orderFile.inputStream.reader()

        return convertOrderFileLineToOrderService(input)

    }
}