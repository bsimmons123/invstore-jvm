package com.invstore.invstorejvm.services.catering

import com.invstore.invstorejvm.models.catering.CateringList
import com.invstore.invstorejvm.models.catering.CateringListCreateDTO
import com.invstore.invstorejvm.models.catering.CateringListDTO
import com.invstore.invstorejvm.services.OperationResult

interface ICateringListService {
    fun findByUserId(userId: Long): OperationResult<List<CateringListDTO>?>
    fun findBySessionId(sessionId: String): Boolean
    fun create(cateringList: CateringListCreateDTO): OperationResult<CateringListDTO?>
    fun update(cateringList: CateringList): OperationResult<CateringListDTO?>
    fun delete(cateringList: CateringList)
    fun findById(id: Long): OperationResult.Success<CateringListDTO?>
}