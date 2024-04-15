package com.invstore.invstorejvm.services.catering

import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.repositories.catering.CateringItemRepository
import com.invstore.invstorejvm.repositories.catering.CateringItemTypeRepository
import com.invstore.invstorejvm.repositories.catering.CateringListRepository
import com.invstore.invstorejvm.services.OperationResult
import org.springframework.stereotype.Service

@Service
class CateringItemTypeService(
    private val cateringItemTypeRepository: CateringItemTypeRepository,
    private val cateringItemRepo: CateringItemRepository,
    private val cateringListRepo: CateringListRepository,
    private val cateringItemService: CateringItemService
) : ICateringItemTypeService {

    override fun findByListId(listId: Long): OperationResult<List<CateringItemTypeDTO>?> {
        val list = cateringItemTypeRepository.findCateringItemTypeByListId(listId)

        val dtoList: List<CateringItemTypeDTO> = list.map { it.toDTO() }

        return OperationResult.Success(dtoList)
    }

    override fun create(cateringItem: CateringItemTypeCreateDTO): OperationResult<CateringItemTypeDTO?> {
        val errors = hashMapOf<String, String>()
        val item = cateringItem.toCateringItemType(cateringListRepo)

        // LABEL
        if (item.label?.isBlank() == true) {
            errors["Label"] = ("Label cannot be blank")
        }

        // DESCRIPTION
        if (item.description?.isBlank() == true) {
            errors["Description"] = ("Description cannot be blank")
        }

        // LIST
        if ((cateringItem.listId ?: 0) <= 0) {
            errors["List"] = "Invalid List"
        }

        // If any errors, throw exception
        if (errors.isNotEmpty()) {
            return OperationResult.Error(errors)
        } else {
            val result = cateringItemTypeRepository.save(item)
            return OperationResult.Success(result.toDTO())
        }
    }

    override fun update(cateringItem: CateringItemTypeEditDTO): OperationResult<CateringItemTypeDTO?> {
        val errors = hashMapOf<String, String>()

        // ID
        if (cateringItem.id.toInt() <= 0) {
            errors["Id"] = "Invalid id. Id must be a valid integer"
        }

        // LABEL
        if (cateringItem.label?.isBlank() == true) {
            errors["Label"] = "Label cannot be blank"
        }

        // DESCRIPTION
        if (cateringItem.description?.isBlank() == true) {
            errors["Description"] = "Description cannot be blank"
        }

        // LIST
        if ((cateringItem.listId ?: 0) <= 0) {
            errors["List"] = "Invalid List"
        }

        // If any errors, throw exception
        if (errors.isNotEmpty()) {
            return OperationResult.Error(errors)
        } else {
            val result = cateringItemTypeRepository.save(cateringItem.toCateringItemTypeDto(cateringItemService, cateringListRepo))
            return OperationResult.Success(result.toDTO())
        }
    }

    override fun delete(cateringItem: CateringItemType) {
        cateringItemTypeRepository.delete(cateringItem)
    }

    override fun findById(id: Long): OperationResult.Success<CateringItemTypeDTO?> {
        val list =  cateringItemTypeRepository.findById(id).get()
        return OperationResult.Success(list.toDTO())
    }
}
