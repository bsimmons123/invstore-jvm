package com.invstore.invstorejvm.repositories.catering

import com.invstore.invstorejvm.models.catering.InviteList
import com.invstore.invstorejvm.models.users.User
import org.springframework.data.jpa.repository.JpaRepository

interface CateringListInviteRepository : JpaRepository<InviteList, Long> {
    fun findInviteListByUserEmail(email: String): List<InviteList>?
    fun findInviteListByWhoInvited(whoInvited: User): List<InviteList>?
    fun findInviteListByRelatedListId(id: Long): List<InviteList>?
}