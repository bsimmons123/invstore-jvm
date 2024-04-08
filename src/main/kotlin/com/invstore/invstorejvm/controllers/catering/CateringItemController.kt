package com.invstore.invstorejvm.controllers.catering

import com.invstore.invstorejvm.ApiResponse
import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.services.ServiceUtils
import com.invstore.invstorejvm.services.catering.CateringItemService
import com.invstore.invstorejvm.services.catering.CateringListService
import com.invstore.invstorejvm.services.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/cateringitem")
@Tag(description = "API endpoint for getting CateringLists", name = "CateringList")
class CateringItemController(
    private val cateringItemService: CateringItemService
    ) {

    private val log = LoggerFactory.getLogger(CateringItemController::class.java)

    @GetMapping("/{id}")
    fun getCateringItem(@PathVariable id: Long): ResponseEntity<ApiResponse<CateringItemDTO?>> {
        log.info("GET /api/v1/cateringitem/$id")

        val result = cateringItemService.findById(id)

        return ServiceUtils.handleResult(result)
    }

    @GetMapping("/list/{id}")
    fun getCateringItemBySessionId(@PathVariable id: Long): ResponseEntity<ApiResponse<List<CateringItemDTO>?>> {
        log.info("GET /api/v1/cateringitem/list/$id")

        val result = cateringItemService.findByListId(id)

        return ServiceUtils.handleResult(result)
    }

    @PostMapping("/")
    fun createCateringItem(@RequestBody cateringItem: CateringItemCreateDTO): ResponseEntity<ApiResponse<CateringItemDTO?>> {
        log.info("POST /api/v1/cateringitem/")

        val result = cateringItemService.create(cateringItem)

        return ServiceUtils.handleResult(result)
    }

    @PutMapping("/")
    fun updateCateringItem(@RequestBody cateringItem: CateringItemEditDTO): ResponseEntity<ApiResponse<CateringItemDTO?>> {
        log.info("PUT /api/v1/cateringitem/")

        val result = cateringItemService.update(cateringItem)

        return ServiceUtils.handleResult(result)
    }
}