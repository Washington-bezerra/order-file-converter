package br.com.luizalabs.orderfileconverter.infra.entities

import br.com.luizalabs.orderfileconverter.domain.ConversionStatus
import br.com.luizalabs.orderfileconverter.domain.FileType
import jakarta.persistence.*
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
    @Enumerated(EnumType.STRING)
    val fileType: FileType,

    @Column(name = "conversion_status")
    @Enumerated(EnumType.STRING)
    val conversionStatus: ConversionStatus,
){
    @CreationTimestamp
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime
}
