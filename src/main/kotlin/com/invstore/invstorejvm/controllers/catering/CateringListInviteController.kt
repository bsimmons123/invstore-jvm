package com.invstore.invstorejvm.controllers.catering

import com.invstore.invstorejvm.ApiResponse
import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.security.requests.SignInRequest
import com.invstore.invstorejvm.services.OperationResult
import com.invstore.invstorejvm.services.ServiceUtils
import com.invstore.invstorejvm.services.catering.CateringListInviteService
import com.invstore.invstorejvm.services.catering.CateringListService
import com.invstore.invstorejvm.services.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/invitelist")
@Tag(description = "API endpoint for interacting with InviteLists", name = "InviteList")
class CateringListInviteController(
    private val cateringListInviteService: CateringListInviteService,
    private val userService: UserService
    ) {

    private val log = LoggerFactory.getLogger(CateringListInviteController::class.java)

    @GetMapping("/{id}")
    fun getInviteById(@PathVariable id: Long, principal: Principal): ResponseEntity<ApiResponse<InviteListDTO?>> {
        log.info("GET /api/v1/invitelist/$id")

        val result = cateringListInviteService.findById(id)

        return ServiceUtils.handleResult(result)
    }

    @GetMapping("/list/{id}")
    fun getInviteByListId(@PathVariable id: Long, principal: Principal): ResponseEntity<ApiResponse<List<InviteListDTO>>> {
        log.info("GET /api/v1/invitelist/list/$id")

        val result = cateringListInviteService.findByListId(id)

        return ServiceUtils.handleResult(result)
    }

    @GetMapping("/sent/{email}")
    fun getInviteBySentEmail(@PathVariable email: String, principal: Principal): ResponseEntity<ApiResponse<List<InviteListDTO>>> {
        log.info("GET /api/v1/invitelist/sent/$email")

        val result = cateringListInviteService.findBySentEmail(email)

        return ServiceUtils.handleResult(result)
    }

    @GetMapping("/received/")
    fun getInviteByReceivedEmail(principal: Principal): ResponseEntity<ApiResponse<List<InviteListDTO>>> {
        log.info("GET /api/v1/invitelist/received/")

        val u = userService.findByUsername(principal.name)

        val result = cateringListInviteService.findByEmail(u.data?.email ?: "")

        return ServiceUtils.handleResult(result)
    }

    data class InviteRequest(val id: Long)

    @PostMapping("/join/")
    fun acceptInvite(@RequestBody invite: InviteRequest, principal: Principal): ResponseEntity<ApiResponse<InviteListDTO?>> {
        log.info("GET /api/v1/invitelist/join/${invite.id}")

        val u = userService.findByUsername(principal.name)

        val inviteResp = cateringListInviteService.findById(invite.id)

        val inv = when(inviteResp) {
            is OperationResult.Error -> null
            is OperationResult.Success -> inviteResp.data
        }


        inv?.let {
            if (inv.userEmail == u.data?.email) {
                inv.accepted = true
                val result = cateringListInviteService.update( it.toInviteListUpdateDTO() )

                return ServiceUtils.handleResult(result)
            }
        }
        return ServiceUtils.createResponse(null)
    }

    @PostMapping("/")
    fun createCateringList(@RequestBody invite: InviteListCreateDTO, principal: Principal): ResponseEntity<ApiResponse<InviteListDTO?>> {
        log.info("POST /api/v1/invitelist/")

        val u = userService.findByUsername(principal.name)

        if (u.data != null) {
            invite.whoInvited = u.data
        }

        val result = cateringListInviteService.create(invite)

        return ServiceUtils.handleResult(result)
    }

    @PutMapping("/")
    fun updateCateringList(@RequestBody invite: InviteListUpdateDTO, principal: Principal): ResponseEntity<ApiResponse<InviteListDTO?>> {
        log.info("PUT /api/v1/invitelist/")

        val usr = userService.findByUsername(principal.name)
        invite.whoInvited = usr.data

        val result = cateringListInviteService.update(invite)

        return ServiceUtils.handleResult(result)
    }
}