package com.invstore.invstorejvm.controllers

import com.invstore.invstorejvm.factories.Path
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/image")
class ImageController {


    @GetMapping("/user/{id}")
    fun getImage(@PathVariable id: Long): ResponseEntity<Resource?> {
        val imagePath = "images/${Path.USER.value}/$id-userAvatar.png" // Get path from DB based on id

        val file = UrlResource("file:$imagePath")
        return if (file.exists() || file.isReadable) {
            ResponseEntity.ok().header("Content-Type", "image/png").body(file)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/list/{id}")
    fun getImageList(@PathVariable id: Long): ResponseEntity<Resource?> {
        val imagePath = "images/${Path.LIST.value}/$id-listIcon.png" // Get path from DB based on id

        val file = UrlResource("file:$imagePath")
        return if (file.exists() || file.isReadable) {
            ResponseEntity.ok().header("Content-Type", "image/png").body(file)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}