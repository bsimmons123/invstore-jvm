package com.invstore.invstorejvm.controllers.catering

import com.invstore.invstorejvm.ApiResponse
import com.invstore.invstorejvm.models.catering.CateringListCreateDTO
import com.invstore.invstorejvm.models.catering.CateringListDTO
import com.invstore.invstorejvm.models.catering.CateringListEditDTO
import com.invstore.invstorejvm.security.controllers.MissingAccessException
import com.invstore.invstorejvm.services.AccessManagement
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
@RequestMapping("/api/v1/cateringlist")
@Tag(description = "API endpoint for getting CateringLists", name = "CateringList")
class CateringListController(
    private val cateringListService: CateringListService,
    private val userService: UserService,
    private val cateringListInviteService: CateringListInviteService
) {

    private val log = LoggerFactory.getLogger(CateringListController::class.java)

    @GetMapping("/{id}")
    fun getCateringList(@PathVariable id: Long, principal: Principal): ResponseEntity<ApiResponse<CateringListDTO?>> {
        log.info("GET /api/v1/cateringlist/$id")

        val result = cateringListService.findById(id)

        val list = AccessManagement.extractObjectNull(result)

        if (list != null && AccessManagement.hasAccessToList(list.id, principal, userService, cateringListService)) {
            return ServiceUtils.createResponse(list)
        } else {
            throw MissingAccessException("Not able to access list")
        }
    }

    @GetMapping("/session/{sessionId}")
    fun getCateringListBySessionId(@PathVariable sessionId: String, principal: Principal): ResponseEntity<ApiResponse<CateringListDTO?>> {
        log.info("GET /api/v1/cateringlist/session/$sessionId")

        val result = cateringListService.findBySessionId(sessionId)

        val list = AccessManagement.extractObjectNull(result)

        if (list != null && AccessManagement.hasAccessToList(list.id, principal, userService, cateringListService)) {
            return ServiceUtils.createResponse(list)
        } else {
            throw MissingAccessException("Not able to access list")
        }
    }

    @GetMapping("/user/")
    fun getCateringListByUser(principal: Principal): ResponseEntity<ApiResponse<List<CateringListDTO?>>> {
        log.info("GET /api/v1/cateringlist/user/")

        val u = userService.findByUsername(principal.name)

        val result = cateringListService.findByUserId(u.data?.id ?: 0)

        // Also pull in any lists by proxy of an invitation

        val invResp = cateringListInviteService.findByEmail(u.data?.email ?: "")
        val inv = when (invResp) {
            is OperationResult.Error -> null
            is OperationResult.Success -> invResp.data
        }
        val cateringLists = inv?.map { invite ->
                val list = when (val listResp = cateringListService.findById(invite.relatedList.id)) {
                    is OperationResult.Error -> null
                    is OperationResult.Success -> listResp.data
                }
                if (list != null && list.user.id != u.data?.id) {
                    list
                } else
                    null
        }

        val res = when (result) {
            is OperationResult.Error -> null
            is OperationResult.Success -> result.data
        }

        val allCateringLists: List<CateringListDTO> =
            ((cateringLists ?: emptyList()) + (res ?: emptyList())).mapNotNull { it }


        return ServiceUtils.createResponse(allCateringLists)
    }

    @PostMapping("/")
    fun createCateringList(@RequestBody cateringList: CateringListCreateDTO, principal: Principal): ResponseEntity<ApiResponse<CateringListDTO?>> {
        log.info("POST /api/v1/cateringlist/")

        val u = userService.findByUsername(principal.name)

        if (u.data != null) {
            cateringList.user = u.data
        }

        val result = cateringListService.create(cateringList)

        return ServiceUtils.handleResult(result)
    }

    @PutMapping("/")
    fun updateCateringList(@RequestBody cateringList: CateringListEditDTO, principal: Principal): ResponseEntity<ApiResponse<CateringListDTO?>> {
        log.info("PUT /api/v1/cateringlist/")

        val result = cateringListService.update(cateringList)

        return ServiceUtils.handleResult(result)
    }
}