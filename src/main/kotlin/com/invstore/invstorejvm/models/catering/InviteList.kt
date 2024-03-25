package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.models.users.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "c_invite_list")
data class InviteList(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_email", nullable=false)
    var userEmail: String,

    @ManyToOne @JoinColumn(name = "who_invited_id", nullable=false)
    var whoInvited: User,

    @ManyToOne @JoinColumn(name = "list_id", nullable=false)
    var relatedList: CateringList,

    var accepted: Boolean = false,

    @Column(name = "date_sent")
    var dateSent: LocalDateTime = LocalDateTime.now()
)