package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.repositories.catering.CateringItemRepository
import com.invstore.invstorejvm.repositories.catering.CateringListRepository
import com.invstore.invstorejvm.services.catering.CateringItemService
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "c_item_type")
data class CateringItemType(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var label: String? = null,

    var description: String? = null,

    @ManyToOne @JoinColumn(name="list_id")
    var list: CateringList? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun toDTO() : CateringItemTypeDTO = CateringItemTypeDTO(
        id = this.id,
        label = this.label,
        listId = this.list?.id ?: 0,
        description = this.description
    )
}

data class CateringItemTypeCreateDTO(
    var id: Long,
    var label: String?,
    var listId: Long?,
    var description: String?,
    var quantity: Int?,
    var status: CateringItem.Status?
) {
    fun toCateringItemType(cateringItemRepo: CateringItemRepository, cateringListRepository: CateringListRepository): CateringItemType {
        val item = cateringItemRepo.findById(this.id).get()
        val list = cateringListRepository.findById(listId ?: 0)

        if (list.isEmpty){
            throw IllegalArgumentException("Catering list not found")
        }

        return CateringItemType(
            id = this.id,
            label = this.label ?: item.label,
            description = this.description ?: item.description,
            list = list.get()
        )
    }
}

data class CateringItemTypeEditDTO(
    var id: Long,
    var label: String?,
    var listId: Long?,
    var description: String?,
    var quantity: Int?,
    var status: CateringItem.Status?
) {
    fun toCateringItemTypeDto(cateringItemService: CateringItemService, cateringListRepository: CateringListRepository): CateringItemType {
        val item = cateringItemService.findById(this.id).data!!
        val list = cateringListRepository.findById(listId ?: 0)

        if (list.isEmpty){
            throw IllegalArgumentException("Catering list not found")
        }

        return CateringItemType(
            id = this.id,
            label = this.label ?: item.label,
            description = this.description ?: item.description,
            list = list.get()
        )
    }
}

data class CateringItemTypeDTO(
    var id: Long,
    var label: String?,
    var listId: Long,
    var description: String?,
)