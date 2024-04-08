package com.invstore.invstorejvm.models.catering

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "c_item")
data class CateringItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var label: String? = null,

    @ManyToOne @JoinColumn(name = "type_id")
    var type: CateringItemType? = null,

    @ManyToOne @JoinColumn(name = "list_id")
    var list: CateringList? = null,

    var description: String? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    var quantity: Int = 1,

    @Enumerated(EnumType.STRING)
    var status: Status = Status.PENDING
) {
    enum class Status { PENDING, CONFIRMED, DELIVERED }
}

data class CateringItemCreateDTO(
    var label: String?,
    var type: CateringItemType?,
    var list: CateringListCreateDTO?,
    var description: String?,
    var quantity: Int?,
    var status: CateringItem.Status
) {
    fun toCateringItemDto(): CateringItem {
        return CateringItem(
            id = 0,
            label = this.label,
            type = this.type,
            description = this.description,
            list = this.list?.toCateringList(),
            quantity = this.quantity ?: 1,
            status = this.status
        )
    }
}