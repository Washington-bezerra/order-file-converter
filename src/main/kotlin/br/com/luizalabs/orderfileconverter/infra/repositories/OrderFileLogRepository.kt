package br.com.luizalabs.orderfileconverter.infra.repositories

import br.com.luizalabs.orderfileconverter.infra.entities.OrderFileLog
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderFileLogRepository : JpaRepository<OrderFileLog, UUID> {
}