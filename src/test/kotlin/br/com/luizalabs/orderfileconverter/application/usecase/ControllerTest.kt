package br.com.luizalabs.orderfileconverter.application.usecase

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application-test.yml"])
class ControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `test`(){

                val result = mockMvc.multipart("/v1/convert/order-file") {
                    file("file", "0000000002                                     Medeiros00000123450000000122      256.2420201201".toByteArray(charset("UTF8")))
                    header("userName", "tester")
                }

        println(">>>>> " + result.andReturn().response.contentAsString)
        println(">>>>> " + result.andReturn().response.status)
    }
}