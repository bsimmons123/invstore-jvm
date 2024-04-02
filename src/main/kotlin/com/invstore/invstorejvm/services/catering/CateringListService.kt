package com.invstore.invstorejvm.services.catering

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.invstore.invstorejvm.models.catering.CateringList
import com.invstore.invstorejvm.models.catering.CateringListCreateDTO
import com.invstore.invstorejvm.models.catering.CateringListDTO
import com.invstore.invstorejvm.models.users.UserDTO
import com.invstore.invstorejvm.repositories.catering.CateringListRepository
import com.invstore.invstorejvm.services.OperationResult
import io.swagger.v3.core.util.Json
import org.springframework.stereotype.Service

@Service
class CateringListService(
    private val cateringListRepository: CateringListRepository
) : ICateringListService {
    override fun findByUserId(userId: Long): OperationResult<List<CateringListDTO>?> {
        val list = cateringListRepository.findCateringListsByUserId(userId)

        val dtoList: List<CateringListDTO>? = list?.map { it.toCateringListDTO() }

        return OperationResult.Success(dtoList)
    }

    override fun findBySessionId(sessionId: String): Boolean {
        val list = cateringListRepository.findCateringListsBySessionId(sessionId)
        return list != null
    }

    override fun create(cateringList: CateringListCreateDTO): OperationResult<CateringListDTO?> {
        val errors = hashMapOf<String, String>()
        val list = cateringList.toCateringList()

        // USER
        if (list.user.id.toInt() == 0) {
            errors["User"] = "User must be set."
        }

        // LABEL
        if (list.label?.isBlank() == true) {
            errors["Label"] = ("Label cannot be blank")
        }

        // DESCRIPTION
        if (list.description?.isBlank() == true) {
            errors["Description"] = ("Description cannot be blank")
        }

        // IS ACTIVE
        if (!list.isActive) {
            errors["IsActive"] = ("Cannot create an inactive List")
        }

        // ITEM LIMIT
        if (list.itemLimit == null) {
            // Default item limit to 100 for an empty itemLimit
            list.itemLimit = 100
        }

        // MAX USERS
        if (list.maxUsers == null) {
            // Default maxUsers to 10 for an empty itemLimit
            list.maxUsers = 10
        }

        // NOTES
        if ((list.notes ?: "").length > 1000) {
            errors["Notes"] = ("Notes cannot exceed 1000 characters")
        }

        // Generate sessionId now and overwrite the value sent (should be null)
        list.sessionId = generateSessionId()

        // If any errors, throw exception
        if (errors.isNotEmpty()) {
            return OperationResult.Error(errors)
        } else {
            val result = cateringListRepository.save(list).toCateringListDTO()
            return OperationResult.Success(result)
        }
    }

    override fun update(cateringList: CateringList): OperationResult<CateringListDTO?> {
        val errors = hashMapOf<String, String>()

        // ID
        if (cateringList.id.toInt() <= 0) {
            errors["Id"] = "Invalid id. Id must be a valid integer"
        }

        // LABEL
        if (cateringList.label?.isBlank() == true) {
            errors["Label"] = "Label cannot be blank"
        }

        // DESCRIPTION
        if (cateringList.description?.isBlank() == true) {
            errors["Description"] = "Description cannot be blank"
        }

        // USER
        if (cateringList.user.id <= 0) {
            errors["User"] = "Invalid User"
        }

        // ITEM LIMIT
        if (cateringList.itemLimit == null) {
            // Default item limit to 100 for an empty itemLimit
            errors["ItemLimit"] = "Cannot have a blank item limit"
        }

        // MAX USERS
        if (cateringList.maxUsers == null || cateringList.maxUsers == 0) {
            // Default maxUsers to 10 for an empty itemLimit
            errors["MaxUsers"] = "Cannot set Max Users to 0"
        }

        // NOTES
        if ((cateringList.notes ?: "").length > 1000) {
            errors["Notes"] = "Notes cannot exceed 1000 characters"
        }

        // If any errors, throw exception
        if (errors.isNotEmpty()) {
            return OperationResult.Error(errors)
        } else {
            val result = cateringListRepository.save(cateringList)
            return OperationResult.Success(result.toCateringListDTO())
        }
    }

    override fun delete(cateringList: CateringList) {
        cateringListRepository.delete(cateringList)
    }

    override fun findById(id: Long): OperationResult.Success<CateringListDTO?> {
        val list =  cateringListRepository.findById(id).get()
        return OperationResult.Success(list.toCateringListDTO())
    }

    /**
     * Generates a VALID session ID.
     *
     * @return The generated session ID as a String.
     */
    fun generateSessionId(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        var sessionId = ""
        var valid = false
        while (!valid) {
            sessionId = (1..8).map { allowedChars.random() }.joinToString("")
            valid = validateSessionId(sessionId)
        }
        return sessionId
    }

    private fun validateSessionId(sessionId: String): Boolean {
        return !findBySessionId(sessionId)
    }
}
