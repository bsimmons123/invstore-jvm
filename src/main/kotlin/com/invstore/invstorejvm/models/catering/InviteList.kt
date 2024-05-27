package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.models.users.UserDTO
import com.invstore.invstorejvm.repositories.catering.CateringListRepository
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
) {
    fun toInviteListDTO(): InviteListDTO {
        return InviteListDTO(
            id = this.id,
            userEmail = this.userEmail,
            whoInvited = this.whoInvited.toUserDTO(),
            relatedList = this.relatedList.toCateringListDTO(),
            accepted = this.accepted,
            dateSent = this.dateSent
        )
    }
}

data class InviteListDTO(
    val id: Long,
    val userEmail: String,
    val whoInvited: UserDTO,
    val relatedList: CateringListDTO,
    val accepted: Boolean,
    val dateSent: LocalDateTime
)

data class InviteListCreateDTO(
    var id: Long?,
    var userEmail: String?,
    var whoInvited: UserDTO?,
    var relatedList: Long?,
) {
    fun toInviteList(cateringListRepository: CateringListRepository): InviteList {
        val list = cateringListRepository.findById(relatedList ?: 0)
        return InviteList(
            id = 0,
            userEmail = this.userEmail!!,
            whoInvited = this.whoInvited!!.toUser(),
            relatedList = list.get(),
            accepted = false,
        )
    }
}