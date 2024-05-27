package com.invstore.invstorejvm.services.catering

import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.services.OperationResult

interface ICateringListInviteService {
    fun create(invite: InviteListCreateDTO): OperationResult<InviteListDTO?>
    fun update(invite: InviteListCreateDTO): OperationResult<InviteListDTO?>
    fun delete(invite: InviteList)
    fun findById(id: Long): OperationResult<InviteListDTO?>
    fun findByEmail(email: String): OperationResult<List<InviteListDTO>>
    fun findBySentEmail(email: String): OperationResult<List<InviteListDTO>>
    fun findByListId(listId: Long): OperationResult<List<InviteListDTO>>
}