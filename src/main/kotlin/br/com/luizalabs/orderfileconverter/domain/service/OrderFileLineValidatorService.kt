package br.com.luizalabs.orderfileconverter.domain.service

import br.com.luizalabs.orderfileconverter.infra.exception.UnprocessableEntityException
import org.springframework.stereotype.Service

@Service
class OrderFileLineValidatorService {
    fun validate(line: String) : Boolean{
        if (line.length != 95){
            throw UnprocessableEntityException("The size of all rows must be 95")
        }

        return true
    }
}