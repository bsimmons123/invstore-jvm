package com.invstore.invstorejvm.repositories.catering

import com.invstore.invstorejvm.models.catering.CateringItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CateringItemRepository : JpaRepository<CateringItem, Long> {
    fun findCateringItemByListId(listId: Long): List<CateringItem>?

    @Query("select ci from CateringItem ci join ci.list cl where cl.user.id = :userId")
    fun findCateringItemsByUserId(userId: Long): List<CateringItem>?
}