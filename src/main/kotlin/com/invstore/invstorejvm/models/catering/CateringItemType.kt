package com.invstore.invstorejvm.models.catering

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "c_item_type")
data class CateringItemType(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var label: String? = null,

    @ManyToOne @JoinColumn(name="list_id")
    var list: CateringList? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)