package br.com.luizalabs.orderfileconverter.application.usecases

import br.com.luizalabs.orderfileconverter.domain.ConversionStatus
import br.com.luizalabs.orderfileconverter.domain.FileType
import br.com.luizalabs.orderfileconverter.domain.Order
import br.com.luizalabs.orderfileconverter.domain.service.ConvertOrderFileLineToOrderService
import br.com.luizalabs.orderfileconverter.domain.service.GenerateSHA256HashService
import br.com.luizalabs.orderfileconverter.infra.entities.OrderFileLog
import br.com.luizalabs.orderfileconverter.infra.repositories.OrderFileLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*
import org.slf4j.LoggerFactory

@Component
class OrderFileConverterUseCase {

    private val logger = LoggerFactory.getLogger(OrderFileConverterUseCase::class.java)

    @Autowired
    lateinit var convertOrderFileLineToOrderService: ConvertOrderFileLineToOrderService

    @Autowired
    lateinit var generateSHA256HashService: GenerateSHA256HashService

    @Autowired
    lateinit var orderFileLogRepository: OrderFileLogRepository

    operator fun invoke(orderFile: MultipartFile, userName: String): MutableMap<Int, MutableList<Order>> {

        val input = orderFile.inputStream.reader()

        lateinit var ordersGrouped: MutableMap<Int, MutableList<Order>>
        lateinit var  conversionStatus: ConversionStatus
        var fileHash: String? = null


        try{
            ordersGrouped = convertOrderFileLineToOrderService(input)
            fileHash = generateSHA256HashService(orderFile)
            conversionStatus = ConversionStatus.CONVERTED
        }catch (e: Exception){
            logger.error("Error converting file", e)
            conversionStatus = ConversionStatus.ERROR
            throw e
        }finally {
            val orderFileLog = OrderFileLog(
                id = UUID.randomUUID(),
                userName = userName,
                fileHash = fileHash!!,
                fileType = FileType.ORDER,
                conversionStatus = conversionStatus,
            )

            orderFileLogRepository.save(orderFileLog)
        }

        return ordersGrouped

    }
}