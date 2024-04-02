package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.models.users.UserDTO
import com.invstore.invstorejvm.services.catering.CateringListService
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "c_list")
data class CateringList(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var label: String?,
    var description: String?,
    var maxUsers: Int?,

    @Column(name="session_id", nullable=false)
    var sessionId: String?,

    var itemLimit: Int?,

    @ManyToOne @JoinColumn(name="user_id")
    var user: User,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_active")
    var isActive: Boolean,

    var location: String?,

    @Column(name = "notes", length = 1000)
    var notes: String? = "",

    @Enumerated(EnumType.STRING)
    var visibility: Visibility = Visibility.PUBLIC
) {
    enum class Visibility { PUBLIC, PRIVATE, INVITE_ONLY }

    fun toCateringListDTO(): CateringListDTO {
        return CateringListDTO(
            id = this.id,
            label = this.label,
            description = this.description,
            maxUsers = this.maxUsers,
            sessionId = this.sessionId,
            itemLimit = this.itemLimit,
            user = this.user.toUserDTO(),
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            isActive = this.isActive,
            location = this.location,
            visibility = this.visibility,
            notes = this.notes
        )
    }
}

data class CateringListDTO(
    val id: Long,

    val label: String?,
    val description: String?,
    val maxUsers: Int?,
    val sessionId: String?,
    val itemLimit: Int?,

    val user: UserDTO,

    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,

    val isActive: Boolean,

    val location: String?,

    val visibility: CateringList.Visibility,
    val notes: String?
)

data class CateringListCreateDTO(
    val label: String?,
    val description: String?,
    var maxUsers: Int?,
    var itemLimit: Int?,
    var user: UserDTO?,
    val isActive: Boolean,
    val location: String?,
    val visibility: CateringList.Visibility,
    val notes: String?
) {
    fun toCateringList(): CateringList {
        return CateringList(
            id = 0,
            description = this.description,
            label = this.label,
            isActive = this.isActive,
            itemLimit = this.itemLimit,
            location = this.location,
            maxUsers = this.maxUsers,
            sessionId = null, // this will be set in the service layer
            user = this.user?.toUser()!!, // Error if null
            notes = this.notes
        )
    }
}

data class CateringListEditDTO(
    val id: Long,
    val label: String?,
    val description: String?,
    var maxUsers: Int?,
    var itemLimit: Int?,
    val user: UserDTO?,
    val isActive: Boolean?,
    val location: String?,
    val visibility: CateringList.Visibility?,
    val notes: String?
) {
    fun toCateringList(cateringListService: CateringListService): CateringList {
        val oldList = cateringListService.findById(this.id).data!!
        return CateringList(
            id = this.id,
            label = this.label ?: oldList.label,
            description = this.description ?: oldList.description,
            sessionId = oldList.sessionId, // this cannot be updated
            itemLimit = this.itemLimit ?: oldList.itemLimit,
            maxUsers = this.maxUsers ?: oldList.maxUsers,
            user = this.user?.toUser() ?: oldList.user.toUser(),
            location = this.location ?: oldList.location,
            visibility = this.visibility ?: oldList.visibility,
            isActive = this.isActive ?: oldList.isActive,
            notes = this.notes ?: oldList.notes
        )
    }
}