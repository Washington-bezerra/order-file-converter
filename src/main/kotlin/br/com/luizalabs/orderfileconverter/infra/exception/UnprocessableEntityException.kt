package br.com.luizalabs.orderfileconverter.infra.exception

import org.springframework.http.HttpStatus
import java.io.IOException

class UnprocessableEntityException(message: String) : Exception(message){
    val code = HttpStatus.UNPROCESSABLE_ENTITY
}