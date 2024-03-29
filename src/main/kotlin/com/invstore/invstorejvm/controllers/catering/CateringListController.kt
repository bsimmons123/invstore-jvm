package com.invstore.invstorejvm.controllers.catering

import org.slf4j.LoggerFactory
import com.invstore.invstorejvm.ApiResponse
import com.invstore.invstorejvm.models.catering.CateringList
import com.invstore.invstorejvm.models.catering.CateringListDTO
import com.invstore.invstorejvm.security.services.UserDetailsImpl
import com.invstore.invstorejvm.services.ServiceUtils
import com.invstore.invstorejvm.services.catering.CateringListService
import com.invstore.invstorejvm.services.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
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
    fun getCateringList(@PathVariable id: Long, principal: Principal): ResponseEntity<ApiResponse<CateringList>> {
        log.info("GET /api/v1/cateringlist/$id")

        val cateringList = cateringListService.findById(id)

        return ResponseEntity.ok(ApiResponse(true, cateringList, null))
    }

    @GetMapping("/user/")
    fun getCateringListByUser(principal: Principal): ResponseEntity<ApiResponse<List<CateringListDTO>>> {
        log.info("GET /api/v1/cateringlist/user/")

        val u = userService.findByUsername(principal.name)

        val errorList = mutableListOf<String>()
        val result = ServiceUtils.handleServiceCall({ cateringListService.findByUserId(u?.id ?: 0) }, errorList)


        return ResponseEntity.ok(ApiResponse(true, result, null))
    }

    @PostMapping("/")
    fun createCateringList(@RequestBody cateringList: CateringList, principal: Principal): ResponseEntity<ApiResponse<CateringList>> {
        log.info("POST /api/v1/cateringlist/")

        val errorList = mutableListOf<String>()
        val result = ServiceUtils.handleServiceCall({ cateringListService.create(cateringList) }, errorList)

        return if (result != null) ResponseEntity.ok(ApiResponse(true, result, null))
        else ResponseEntity.badRequest().body(ApiResponse(false, null, errorList))
    }

    @PutMapping("/")
    fun updateCateringList(@RequestBody cateringList: CateringList, principal: Principal): ResponseEntity<ApiResponse<CateringList>> {
        log.info("PUT /api/v1/cateringlist/")

        val errorList = mutableListOf<String>()
        val result = ServiceUtils.handleServiceCall({ cateringListService.update(cateringList) }, errorList)

        return if (result != null) ResponseEntity.ok(ApiResponse(true, result, null))
        else ResponseEntity.badRequest().body(ApiResponse(false, null, errorList))
    }
}