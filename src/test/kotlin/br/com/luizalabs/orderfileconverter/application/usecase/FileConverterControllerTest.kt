package br.com.luizalabs.orderfileconverter.application.usecase

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest(properties = ["spring.profiles.active=test"])
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application-test.yml"])
class FileConverterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `When file and header is valid should return 200`(){
        val expectedResponse = """[{"user_id":1,"name":"Zarelli","orders":[{"order_id":123,"total":1024.48,"date":"2021-12-01","products":[{"product_id":111,"value":512.24},{"product_id":122,"value":512.24}]}]},{"user_id":2,"name":"Medeiros","orders":[{"order_id":12345,"total":512.48,"date":"2020-12-01","products":[{"product_id":111,"value":256.24},{"product_id":122,"value":256.24}]}]}]"""
        val filePath = Paths.get("src/test/kotlin/br/com/luizalabs/orderfileconverter/data/data_menor.txt")
        val fileBytes = Files.readAllBytes(filePath)

        val result = mockMvc.multipart("/v1/convert/order-file") {
            file("file", fileBytes)
            header("userName", "userTester")
        }

        assertEquals(expectedResponse, result.andReturn().response.contentAsString)
        assertEquals(200, result.andReturn().response.status)
    }

    @Test
    fun `When file is valid but header is not sent should return 400`(){
        val filePath = Paths.get("src/test/kotlin/br/com/luizalabs/orderfileconverter/data/data_menor.txt")
        val fileBytes = Files.readAllBytes(filePath)

        val result = mockMvc.multipart("/v1/convert/order-file") {
            file("file", fileBytes)
        }

        assertEquals(400, result.andReturn().response.status)
    }

    @Test
    fun `When file is not sent should return 400`(){
        val result = mockMvc.multipart("/v1/convert/order-file") {
            header("userName", "userTester")
        }

        assertEquals(400, result.andReturn().response.status)
    }

    @Test
    fun `When the file line is the wrong size should return 422`(){
        val result = mockMvc.multipart("/v1/convert/order-file") {
            file("file", "0 ".toByteArray(charset("UTF8")))
            header("userName", "tester")
        }

        assertEquals(422, result.andReturn().response.status)
    }
}