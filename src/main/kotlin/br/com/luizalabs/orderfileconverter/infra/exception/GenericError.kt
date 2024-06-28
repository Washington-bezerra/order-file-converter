package br.com.luizalabs.orderfileconverter.infra.exception

import org.springframework.http.HttpStatus
import java.io.IOException

data class GenericError(
    val status: HttpStatus,
    val message: String
)