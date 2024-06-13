package com.invstore.invstorejvm.services.catering

import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.services.OperationResult

interface ICateringItemService {
    fun findByListId(userId: Long): OperationResult<List<CateringItemDTO>?>
    fun create(cateringItem: CateringItemCreateDTO): OperationResult<CateringItemDTO?>
    fun update(cateringItem: CateringItemEditDTO): OperationResult<CateringItemDTO?>
    fun delete(cateringItem: CateringItem)
    fun findById(id: Long): OperationResult.Success<CateringItemDTO?>
    fun findByUserId(userId: Long): OperationResult<List<CateringItemDTO?>>
}