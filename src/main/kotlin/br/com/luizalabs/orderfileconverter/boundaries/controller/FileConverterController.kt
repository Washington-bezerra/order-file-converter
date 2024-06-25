package br.com.luizalabs.orderfileconverter.boundaries.controller

import br.com.luizalabs.orderfileconverter.boundaries.mapper.OrderMapper
import br.com.luizalabs.orderfileconverter.boundaries.response.OrderConverterResponse
import br.com.luizalabs.orderfileconverter.domain.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@RestController
class FileConverterController {

    @Autowired
    lateinit var mapper: OrderMapper

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun orderConvert(@RequestParam("file") file: MultipartFile): ResponseEntity<ArrayList<OrderConverterResponse>> {
//        val fileHash = generateSHA256Hash(file)

        val reader = BufferedReader(InputStreamReader(file.inputStream))
        val ordersGrouped = mutableMapOf<Int, MutableList<Order>>()

        reader.forEachLine { line ->
            val order = Order(
                line.substring(0, 10).trim().toInt(),
                line.substring(10, 55).trim(),
                line.substring(55, 65).trim().toInt(),
                line.substring(65, 75).trim().toInt(),
                BigDecimal(line.substring(75, 87).trim()),
                SimpleDateFormat("yyyyMMdd").parse(line.substring(87, 95).trim())
            )
            ordersGrouped.getOrPut(order.user) { mutableListOf() }.add(order)
        }
        reader.close()

        val orderConverterResponses = mapper.toOrderConverterResponses(ordersGrouped)
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