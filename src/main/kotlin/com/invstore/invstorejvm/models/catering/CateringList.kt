package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.controllers.user.UserDTO
import com.invstore.invstorejvm.models.users.User
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

    @Enumerated(EnumType.STRING)
    var visibility: Visibility = Visibility.PUBLIC
) {
    enum class Visibility { PUBLIC, PRIVATE, INVITE_ONLY }
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

    val visibility: CateringList.Visibility
)