package br.com.luizalabs.orderfileconverter.domain.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.mock.web.MockMultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test

class GenerateSHA256HashServiceTest {

    val generateSHA256HashService: GenerateSHA256HashService = GenerateSHA256HashService()

    @Test
    fun `should return file hash`() {
        val path = Paths.get("src/test/kotlin/br/com/luizalabs/orderfileconverter/data/data_menor.txt")
        val content = Files.readAllBytes(path)
        val multipartFile = MockMultipartFile("file", "data_menor.txt", "text/plain", content)

        val result = generateSHA256HashService(multipartFile)

        assertEquals(result, "kfi+1nLoEIVzg70JLgTCfNQwpFltr5QNvnVI4ErbLp8=")
    }

}