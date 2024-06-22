package br.com.luizalabs.order_file_converter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrderFileConverterApplication

fun main(args: Array<String>) {
	runApplication<OrderFileConverterApplication>(*args)
}
