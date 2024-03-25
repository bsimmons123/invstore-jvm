package com.invstore.invstorejvm.models.users

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_list")
data class UserList(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    var user: User,

    @Column(name = "list_id")
    var listId: Long,

    @Column(length = 50)
    var role: String,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)