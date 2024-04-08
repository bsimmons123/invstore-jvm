package com.invstore.invstorejvm.repositories.catering

import com.invstore.invstorejvm.models.catering.CateringItemType
import org.springframework.data.jpa.repository.JpaRepository

interface CateringItemTypeRepository : JpaRepository<CateringItemType, Long> {
    fun findCateringItemTypeByListId(listId: Long): List<CateringItemType>
}