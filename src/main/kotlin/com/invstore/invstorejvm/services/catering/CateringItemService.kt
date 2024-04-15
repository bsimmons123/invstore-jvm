package com.invstore.invstorejvm.services.catering

import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.repositories.catering.CateringItemRepository
import com.invstore.invstorejvm.repositories.catering.CateringItemTypeRepository
import com.invstore.invstorejvm.repositories.catering.CateringListRepository
import com.invstore.invstorejvm.services.OperationResult
import org.springframework.stereotype.Service

@Service
class CateringItemService(
    private val cateringItemRepository: CateringItemRepository,
    private val cateringListRepo: CateringListRepository,
    private val cateringItemTypeRepository: CateringItemTypeRepository
) : ICateringItemService {

    override fun findByListId(userId: Long): OperationResult<List<CateringItemDTO>?> {
        val list = cateringItemRepository.findCateringItemByListId(userId)

        val dtoList: List<CateringItemDTO>? = list?.map { it.toCateringItemDTO() }

        return OperationResult.Success(dtoList)
    }

    override fun create(cateringItem: CateringItemCreateDTO): OperationResult<CateringItemDTO?> {
        val errors = hashMapOf<String, String>()
        val item = cateringItem.toCateringItem(cateringListRepo)

        // LABEL
        if (item.label?.isBlank() == true) {
            errors["Label"] = ("Label cannot be blank")
        }

        // DESCRIPTION
        if (item.description?.isBlank() == true) {
            errors["Description"] = ("Description cannot be blank")
        }

        if ((item.type?.id ?: 0) <= 0) {
            errors["TypeId"] = "Type cannot be blank"
        }

        // If any errors, throw exception
        if (errors.isNotEmpty()) {
            return OperationResult.Error(errors)
        } else {
            val result = cateringItemRepository.save(item).toCateringItemDTO()
            return OperationResult.Success(result)
        }
    }

    override fun update(cateringItem: CateringItemEditDTO): OperationResult<CateringItemDTO?> {
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

        // USER
        if ((cateringItem.listId ?: 0) <= 0) {
            errors["List"] = "Invalid List"
        }

        if ((cateringItem.typeId ?: 0) > 0) {
            errors["TypeId"] = "Type cannot be blank"
        }

        // If any errors, throw exception
        if (errors.isNotEmpty()) {
            return OperationResult.Error(errors)
        } else {
            val result = cateringItemRepository.save(cateringItem.toCateringItem(this, cateringListRepo, cateringItemTypeRepository))
            return OperationResult.Success(result.toCateringItemDTO())
        }
    }

    override fun delete(cateringItem: CateringItem) {
        cateringItemRepository.delete(cateringItem)
    }

    override fun findById(id: Long): OperationResult.Success<CateringItemDTO?> {
        val list =  cateringItemRepository.findById(id).get()
        return OperationResult.Success(list.toCateringItemDTO())
    }
}
