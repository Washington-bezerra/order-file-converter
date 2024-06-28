package br.com.luizalabs.orderfileconverter.application.controller

import br.com.luizalabs.orderfileconverter.application.usecase.OrderFileConverterUseCase
import br.com.luizalabs.orderfileconverter.boundaries.mapper.OrderMapper
import br.com.luizalabs.orderfileconverter.infra.exception.GenericError
import br.com.luizalabs.orderfileconverter.infra.exception.UnprocessableEntityException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/convert")
class FileConverterController {

    @Autowired
    lateinit var orderMapper: OrderMapper

    @Autowired
    lateinit var orderFileConverterUseCase: OrderFileConverterUseCase

    private val logger = LoggerFactory.getLogger(OrderFileConverterUseCase::class.java)


    @PostMapping("/order-file")
    @ResponseStatus(HttpStatus.OK)
    fun orderConvert(
        @RequestHeader("userName") userName: String,

        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<*> {
        try{
            val ordersGrouped = orderFileConverterUseCase(file, userName)
            val orderConverterResponses = orderMapper.toOrderConverterResponses(ordersGrouped)
            orderConverterResponses.sortBy { it.userId }

            return ResponseEntity(orderConverterResponses, HttpStatus.OK)
        }catch (ex: UnprocessableEntityException){
            return ResponseEntity(GenericError(HttpStatus.UNPROCESSABLE_ENTITY, ex.message!!), HttpStatus.UNPROCESSABLE_ENTITY)
        }catch (ex: Exception) {
            logger.error("Error processing file: ${ex.message}")
            return ResponseEntity(GenericError(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing file"), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}