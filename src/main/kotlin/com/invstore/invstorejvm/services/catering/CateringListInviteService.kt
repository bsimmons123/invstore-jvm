package com.invstore.invstorejvm.services.catering

import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.repositories.catering.CateringListInviteRepository
import com.invstore.invstorejvm.repositories.catering.CateringListRepository
import com.invstore.invstorejvm.repositories.users.UserRepository
import com.invstore.invstorejvm.services.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CateringListInviteService : ICateringListInviteService {
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var cateringListInviteRepository: CateringListInviteRepository
    @Autowired
    private lateinit var cateringListRepository: CateringListRepository


    override fun create(invite: InviteListCreateDTO): OperationResult<InviteListDTO?> {
        val errors = hashMapOf<String, String>()

        // USER
        if (invite.userEmail.isNullOrBlank()) {
            errors["UserEmail"] = "User must be set."
        }

        // WHO
        if (invite.whoInvited == null) {
            errors["Who"] = "Who cannot be blank"
        }

        // LIST
        if (invite.relatedList == null) {
            errors["List"] = "Must be a valid list"
        }
        val invited = invite.toInviteList(cateringListRepository)

        // If any errors, throw exception
        if (errors.isNotEmpty()) {
            return OperationResult.Error(errors)
        } else {
            val result = cateringListInviteRepository.save(invited).toInviteListDTO()
            return OperationResult.Success(result)
        }
    }

    override fun update(invite: InviteListCreateDTO): OperationResult<InviteListDTO?> {
        val errors = hashMapOf<String, String>()

        // USER
        if (invite.userEmail.isNullOrBlank()) {
            errors["UserEmail"] = "User must be set."
        }

        // WHO
        if (invite.whoInvited == null) {
            errors["Who"] = "Who cannot be blank"
        }

        // LIST
        if (invite.relatedList == null) {
            errors["List"] = "Must be a valid list"
        }
        val invites = invite.toInviteList(cateringListRepository)

        // If any errors, throw exception
        if (errors.isNotEmpty()) {
            return OperationResult.Error(errors)
        } else {
            val result = cateringListInviteRepository.save(invites)
            return OperationResult.Success(result.toInviteListDTO())
        }
    }

    override fun delete(invite: InviteList) {
        cateringListInviteRepository.delete(invite)
    }

    override fun findById(id: Long): OperationResult<InviteListDTO?> {
        val list =  cateringListInviteRepository.findById(id)
        return if (list.isEmpty) {
            val errors = hashMapOf<String, String>()
            errors["EMPTY"] = "No Invite by that ID"
            OperationResult.Error(errors)
        } else {
            OperationResult.Success(list.get().toInviteListDTO())
        }
    }

    override fun findByEmail(email: String): OperationResult<List<InviteListDTO>> {
        return cateringListInviteRepository.findInviteListByUserEmail(email)
            ?.let { list -> OperationResult.Success(list.map { it.toInviteListDTO() }) }
            ?: OperationResult.Error(hashMapOf(Pair("Not Found", "Failed searching for invites"))
        )
    }

    override fun findBySentEmail(email: String): OperationResult<List<InviteListDTO>> {
        val usr = userRepository.findByEmail(email)
        return cateringListInviteRepository.findInviteListByWhoInvited(usr!!)
            ?.let { list -> OperationResult.Success(list.map { it.toInviteListDTO() }) }
            ?: OperationResult.Error(hashMapOf(Pair("Not Found", "Failed searching for invites")))
    }

    override fun findByListId(listId: Long): OperationResult<List<InviteListDTO>> {
        val list = cateringListInviteRepository.findInviteListByRelatedListId(listId)
        return if (list.isNullOrEmpty()) {
            val errors = hashMapOf<String, String>()
            errors["EMPTY"] = "No Invites attached to that list"
            OperationResult.Error(errors)
        } else {
            OperationResult.Success(list.map { it.toInviteListDTO() })
        }
    }
}
