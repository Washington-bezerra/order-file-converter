package br.com.luizalabs.orderfileconverter.application.controller

import br.com.luizalabs.orderfileconverter.application.usecase.OrderFileConverterUseCase
import br.com.luizalabs.orderfileconverter.boundaries.mapper.OrderMapper
import br.com.luizalabs.orderfileconverter.application.response.OrderConverterResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/v1/convert")
class FileConverterController {

    @Autowired
    lateinit var orderMapper: OrderMapper

    @Autowired
    lateinit var orderFileConverterUseCase: OrderFileConverterUseCase

    @PostMapping("/order-file")
    @ResponseStatus(HttpStatus.OK)
    fun orderConvert(
        @RequestHeader("userName") userName: String,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<ArrayList<OrderConverterResponse>> {
//        val fileHash = generateSHA256Hash(file)

        val ordersGrouped = orderFileConverterUseCase(file, userName)
        val orderConverterResponses = orderMapper.toOrderConverterResponses(ordersGrouped)
        orderConverterResponses.sortBy { it.userId }

        return ResponseEntity(orderConverterResponses, HttpStatus.OK)
    }

    fun generateSHA256Hash(file: MultipartFile): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val buffer = ByteArray(8192)
        var bytesRead: Int

        file.inputStream.use { inputStream ->
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                messageDigest.update(buffer, 0, bytesRead)
            }
        }

        return Base64.getEncoder().encodeToString(messageDigest.digest())
    }

}