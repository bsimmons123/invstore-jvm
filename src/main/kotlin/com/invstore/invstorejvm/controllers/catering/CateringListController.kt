package com.invstore.invstorejvm.controllers.catering

import com.invstore.invstorejvm.ApiResponse
import com.invstore.invstorejvm.models.catering.CateringListCreateDTO
import com.invstore.invstorejvm.models.catering.CateringListDTO
import com.invstore.invstorejvm.models.catering.CateringListEditDTO
import com.invstore.invstorejvm.services.ServiceUtils
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
    private val userService: UserService
    ) {

    private val log = LoggerFactory.getLogger(CateringListController::class.java)

    @GetMapping("/{id}")
    fun getCateringList(@PathVariable id: Long, principal: Principal): ResponseEntity<ApiResponse<CateringListDTO?>> {
        log.info("GET /api/v1/cateringlist/$id")

        val result = cateringListService.findById(id)

        return ServiceUtils.handleResult(result)
    }

    @GetMapping("/session/{sessionId}")
    fun getCateringListBySessionId(@PathVariable sessionId: String, principal: Principal): ResponseEntity<ApiResponse<CateringListDTO?>> {
        log.info("GET /api/v1/cateringlist/$sessionId")

        val result = cateringListService.findBySessionId(sessionId)

        return ServiceUtils.handleResult(result)
    }

    @GetMapping("/user/")
    fun getCateringListByUser(principal: Principal): ResponseEntity<ApiResponse<List<CateringListDTO>?>> {
        log.info("GET /api/v1/cateringlist/user/")

        val u = userService.findByUsername(principal.name)

        val result = cateringListService.findByUserId(u.data?.id ?: 0)

        return ServiceUtils.handleResult(result)
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