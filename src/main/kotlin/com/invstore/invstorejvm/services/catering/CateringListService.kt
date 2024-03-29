package com.invstore.invstorejvm.services.catering

import com.fasterxml.jackson.databind.util.Converter
import com.invstore.invstorejvm.controllers.user.UserDTO
import com.invstore.invstorejvm.models.catering.CateringList
import com.invstore.invstorejvm.models.catering.CateringListDTO
import com.invstore.invstorejvm.repositories.catering.CateringListRepository
import com.invstore.invstorejvm.services.isValidNonPositiveInt
import com.invstore.invstorejvm.services.isValidPositiveInt
import org.springframework.stereotype.Service

@Service
class CateringListService(
    private val cateringListRepository: CateringListRepository
) : ICateringListService {
    override fun findByUserId(userId: Long): List<CateringListDTO>? {
        val list = cateringListRepository.findCateringListsByUserId(userId)

        val dtoList: List<CateringListDTO>? = list?.map { convertToDto(it) }
        return dtoList
    }

    override fun findBySessionId(sessionId: String): Boolean {
        val list = cateringListRepository.findCateringListsBySessionId(sessionId)
        return list != null
    }

    override fun create(cateringList: CateringList): CateringList? {
        val errors = mutableListOf<String>()

        // ID
        if (cateringList.id.toInt() != 0) {
            errors.add("Invalid id. Id must be blank")
        }

        // USER
        if (cateringList.user.id.toInt() == 0) {
            errors.add("User must be set.")
        }

        // IS ACTIVE
        if (!cateringList.isActive) {
            errors.add("Cannot create an inactive List")
        }

        // ITEM LIMIT
        if (cateringList.itemLimit == null) {
            // Default item limit to 100 for an empty itemLimit
            cateringList.itemLimit = 100
        }

        // MAX USERS
        if (cateringList.maxUsers == null) {
            // Default maxUsers to 10
            cateringList.maxUsers = 10
        }

        //  Generate sessionId now and overwrite the value sent (should be null)
        cateringList.sessionId = generateSessionId()

        // If any errors, throw exception or handle as per your need
        if (errors.isNotEmpty()) {
            throw IllegalArgumentException(errors.joinToString("| "))
        }
        return cateringListRepository.save(cateringList)
    }

    override fun update(cateringList: CateringList): CateringList? {
        val errors = mutableListOf<String>()

        // ID
        if (cateringList.id.toInt() <= 0) {
            errors.add("Invalid id. Id must be a valid integer")
        }

        // USER
        if (cateringList.user.id > 0) {
            errors.add("Invalid User")
        }

        // ITEM LIMIT
        if (cateringList.itemLimit == null) {
            // Default item limit to 100 for an empty itemLimit
            errors.add("Cannot have a blank item limit")
        }

        // MAX USERS
        if (cateringList.maxUsers == null || cateringList.maxUsers == 0) {
            // Default maxUsers to 10
            errors.add("Cannot set Max Users to 0")
        }

        // If any errors, throw exception or handle as per your need
        if (errors.isNotEmpty()) {
            throw IllegalArgumentException(errors.joinToString("| "))
        }
        return cateringListRepository.save(cateringList)
    }

    override fun delete(cateringList: CateringList) {
        cateringListRepository.delete(cateringList)
    }

    override fun findById(id: Long): CateringList? {
        return cateringListRepository.findById(id).get()
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

    fun convertToDto(entity: CateringList): CateringListDTO {
        return CateringListDTO(
            entity.id,
            entity.label,
            entity.description,
            entity.maxUsers,
            entity.sessionId,
            entity.itemLimit,
            UserDTO(
                username = entity.user.username,
                email = entity.user.email,
                name = entity.user.name,
                id = entity.user.id
            ),
            entity.createdAt,
            entity.updatedAt,
            entity.isActive,
            entity.location,
            entity.visibility
        )
    }
}
