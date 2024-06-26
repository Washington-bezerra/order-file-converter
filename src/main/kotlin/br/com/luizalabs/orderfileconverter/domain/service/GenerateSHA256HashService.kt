package br.com.luizalabs.orderfileconverter.domain.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest
import java.util.*

@Service
class GenerateSHA256HashService {

    operator fun invoke(file: MultipartFile):  String {
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