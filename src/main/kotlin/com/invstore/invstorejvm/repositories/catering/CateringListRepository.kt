package com.invstore.invstorejvm.repositories.catering

import com.invstore.invstorejvm.models.catering.CateringList
import org.springframework.data.jpa.repository.JpaRepository

interface CateringListRepository : JpaRepository<CateringList, Long> {
    fun findCateringListsByUserId(userId: Long): List<CateringList>?
    fun findCateringListsBySessionId(sessionId: String): CateringList?
}