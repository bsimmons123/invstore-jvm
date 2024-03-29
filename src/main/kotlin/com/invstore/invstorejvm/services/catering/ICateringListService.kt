package com.invstore.invstorejvm.services.catering

import com.invstore.invstorejvm.models.catering.CateringList
import com.invstore.invstorejvm.models.catering.CateringListDTO

interface ICateringListService {
    fun findByUserId(userId: Long): List<CateringListDTO>?
    fun findBySessionId(sessionId: String): Boolean
    fun create(cateringList: CateringList): CateringList?
    fun update(cateringList: CateringList): CateringList?
    fun delete(cateringList: CateringList)
    fun findById(id: Long): CateringList?
}