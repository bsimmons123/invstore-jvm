package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.models.users.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "c_list")
data class CateringList(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var label: String? = null,
    var description: String? = null,
    var maxUsers: Int? = null,

    @Column(name="session_id", nullable=false)
    var sessionId: String,

    var itemLimit: Int? = null,
    @ManyToOne @JoinColumn(name="user_id")
    var user: User,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_active")
    var isActive: Boolean = true,

    var location: String? = null,

    @Enumerated(EnumType.STRING)
    var visibility: Visibility = Visibility.PUBLIC
) {
    enum class Visibility { PUBLIC, PRIVATE, INVITE_ONLY }
}