package br.com.luizalabs.orderfileconverter.builder

import org.springframework.mock.web.MockMultipartFile
import java.nio.file.Files
import java.nio.file.Paths

class MultipartfileBuilder {

    companion object {
        fun build(): MockMultipartFile {
            val path = Paths.get("src/test/kotlin/br/com/luizalabs/orderfileconverter/data/data_menor.txt")
            val content = Files.readAllBytes(path)
            val multipartFile = MockMultipartFile("file", "data_menor.txt", "text/plain", content)
            return multipartFile
        }
    }

}