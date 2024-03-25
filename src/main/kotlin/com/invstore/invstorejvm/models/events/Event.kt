package com.invstore.invstorejvm.models.events

import com.invstore.invstorejvm.models.catering.CateringList
import com.invstore.invstorejvm.models.users.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "event")
data class Event(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "date", nullable = false)
    var date: LocalDateTime = LocalDateTime.now(),

    @ManyToOne @JoinColumn(name = "organizer_id", nullable = false)
    var organizer: User,

    @OneToOne @JoinColumn(name = "catering_id")
    var cateringList: CateringList? = null,

    @Column(name = "description")
    var description: String? = null
)