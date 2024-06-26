package br.com.luizalabs.orderfileconverter.infra.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "order_file_log")
data class OrderFileLog(

    @Id
    val id: UUID,

    @Column(name = "user_name")
    val userName: String,

    @Column(name = "file_hash")
    val fileHash: String,

    @Column(name = "file_type")
    val fileType: String,

    @Column
    val status: String,
){
    @CreationTimestamp
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime
}
