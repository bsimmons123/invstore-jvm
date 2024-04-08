package com.invstore.invstorejvm.services.catering

import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.services.OperationResult

interface ICateringItemTypeService {
    fun findByListId(listId: Long): OperationResult<List<CateringItemTypeDTO>?>
    fun create(cateringItem: CateringItemTypeCreateDTO): OperationResult<CateringItemTypeDTO?>
    fun update(cateringItem: CateringItemTypeEditDTO): OperationResult<CateringItemTypeDTO?>
    fun delete(cateringItem: CateringItemType)
    fun findById(id: Long): OperationResult.Success<CateringItemTypeDTO?>
}