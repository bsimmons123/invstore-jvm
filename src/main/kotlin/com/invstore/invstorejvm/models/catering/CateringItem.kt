package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.repositories.catering.CateringListRepository
import com.invstore.invstorejvm.services.catering.CateringItemService
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.NoSuchElementException

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

    fun toCateringItemDTO(): CateringItemDTO {
        return CateringItemDTO(
            id = this.id,
            label = this.label,
            description = this.description,
            listId = this.id,
            status = this.status,
            quantity = this.quantity,
            type = this.type
        )
    }
}

data class CateringItemCreateDTO(
    var label: String?,
    var type: CateringItemType?,
    var listId: Long?,
    var description: String?,
    var quantity: Int?,
    var status: CateringItem.Status
) {
    fun toCateringItem(cateringListRepository: CateringListRepository): CateringItem {
        val list = cateringListRepository.findById(listId ?: 0)

        if (list.isEmpty){
            throw IllegalArgumentException("Catering list not found")
        }

        return CateringItem(
            id = 0,
            label = this.label,
            type = this.type,
            description = this.description,
            list = list.get(),
            quantity = this.quantity ?: 1,
            status = this.status
        )
    }
}

data class CateringItemEditDTO(
    var id: Long,
    var label: String?,
    var type: CateringItemType?,
    var list: Long?,
    var description: String?,
    var quantity: Int?,
    var status: CateringItem.Status?
) {
    fun toCateringItem(cateringItemService: CateringItemService, cateringListRepository: CateringListRepository): CateringItem {
        val item = cateringItemService.findById(this.id).data!!
        val list = cateringListRepository.findById(list ?: 0)
        if (list.isEmpty) {
            throw NoSuchElementException("Catering Item ${id} not found")
        }

        return CateringItem(
            id = this.id,
            label = this.label ?: item.label,
            type = this.type ?: item.type,
            description = this.description ?: item.description,
            list = list.get(),
            quantity = this.quantity ?: 1,
            status = this.status ?: item.status
        )
    }
}

data class CateringItemDTO(
    var id: Long,
    var label: String?,
    var type: CateringItemType?,
    var listId: Long,
    var description: String?,
    var quantity: Int?,
    var status: CateringItem.Status
)