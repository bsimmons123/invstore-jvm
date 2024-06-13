package com.invstore.invstorejvm.repositories.catering

import com.invstore.invstorejvm.models.catering.CateringItemType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CateringItemTypeRepository : JpaRepository<CateringItemType, Long> {
    fun findCateringItemTypeByListId(listId: Long): List<CateringItemType>

    @Query("select cit from CateringItemType cit join cit.list cl where cl.user.id = :userId")
    fun findCateringItemTypesByUserId(userId: Long): List<CateringItemType>
}