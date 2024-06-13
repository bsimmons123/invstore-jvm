package com.invstore.invstorejvm.controllers.catering

import com.invstore.invstorejvm.ApiResponse
import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.security.controllers.MissingAccessException
import com.invstore.invstorejvm.services.AccessManagement
import com.invstore.invstorejvm.services.ServiceUtils
import com.invstore.invstorejvm.services.catering.CateringItemService
import com.invstore.invstorejvm.services.catering.CateringItemTypeService
import com.invstore.invstorejvm.services.catering.CateringListService
import com.invstore.invstorejvm.services.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/cateringitemtype")
@Tag(description = "API endpoint for getting CateringLists", name = "CateringList")
class CateringItemTypeController(
    private val cateringItemTypeService: CateringItemTypeService,
    private val cateringListService: CateringListService,
    private val userService: UserService
    ) {

    private val log = LoggerFactory.getLogger(CateringItemTypeController::class.java)

    @GetMapping("/{id}")
    fun getCateringItem(@PathVariable id: Long): ResponseEntity<ApiResponse<CateringItemTypeDTO?>> {
        log.info("GET /api/v1/cateringitemtype/$id")

        val result = cateringItemTypeService.findById(id)

        return ServiceUtils.handleResult(result)
    }

    @GetMapping("/list/{id}")
    fun getCateringItemBySessionId(@PathVariable id: Long, principal: Principal): ResponseEntity<ApiResponse<List<CateringItemTypeDTO>?>> {
        log.info("GET /api/v1/cateringitemtype/list/$id")

        val result = cateringItemTypeService.findByListId(id)

        val type = AccessManagement.extractObjectNull(result)

        if (type != null && AccessManagement.hasAccessToList(id, principal, userService, cateringListService)) {
            return ServiceUtils.createResponse(type)
        } else {
            throw MissingAccessException("Not able to access list")
        }
    }

    @GetMapping("/list/user")
    fun getCateringItemByUser(principal: Principal): ResponseEntity<ApiResponse<List<CateringItemTypeDTO?>>> {
        log.info("GET /api/v1/cateringitemtype/list/user")

        val u = userService.findByUsername(principal.name)

        val result = cateringItemTypeService.findByUserId(u.data?.id ?: 0)

        return ServiceUtils.handleResult(result)
    }

    @PostMapping("/")
    fun createCateringItem(@RequestBody cateringItem: CateringItemTypeCreateDTO): ResponseEntity<ApiResponse<CateringItemTypeDTO?>> {
        log.info("POST /api/v1/cateringitemtype/")

        val result = cateringItemTypeService.create(cateringItem)

        return ServiceUtils.handleResult(result)
    }

    @PutMapping("/")
    fun updateCateringItem(@RequestBody cateringItem: CateringItemTypeEditDTO): ResponseEntity<ApiResponse<CateringItemTypeDTO?>> {
        log.info("PUT /api/v1/cateringitemtype/")

        val result = cateringItemTypeService.update(cateringItem)

        return ServiceUtils.handleResult(result)
    }
}