package com.invstore.invstorejvm.models.audit

import com.invstore.invstorejvm.models.users.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "audit_log")
data class AuditLog(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "action", nullable = false)
    var action: String,

    @Column(name = "timestamp", nullable = false)
    var timestamp: LocalDateTime = LocalDateTime.now(),

    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "description")
    var description: String? = null
)