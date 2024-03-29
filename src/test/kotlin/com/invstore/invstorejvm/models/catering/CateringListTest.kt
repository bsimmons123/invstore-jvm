package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.InvstoreJvmApplication
import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.services.ServiceUtils
import com.invstore.invstorejvm.services.catering.CateringListService
import com.invstore.invstorejvm.services.isValidPositiveInt
import com.invstore.invstorejvm.services.user.UserService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@SpringBootTest(classes = [InvstoreJvmApplication::class])
@ActiveProfiles("test")
class CateringListTest {
    @Autowired
    private lateinit var cateringListService: CateringListService

    @Autowired
    private lateinit var userService: UserService

    fun buildCateringList(): CateringList {
        var user: User = buildUser()

        user = userService.create(user)!!

        return CateringList(
            id = 0,
            description = "Stinky items only",
            label = "Stink pot",
            isActive = true,
            itemLimit = 10,
            location = "Asgard",
            maxUsers = 2,
            sessionId = null,
            user = user
        )
    }

    fun buildUser(): User {
        return User(
            id = 0,
            name = "Ralph",
            username = "raphie",
            isActive = true,
            email = "ral@self.com",
            password = null,
            timezone = "UTC",
            lastLoggedIn = LocalDateTime.now(),
            provider = null,
            providerId = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun findPersistedUser(): User? {
        return userService.findByUsername("raphie")
    }

    @Test
    @Transactional
    fun `Should persist a cateringList`() {
        var cateringList = buildCateringList()

        cateringList = cateringListService.create(cateringList)!!

        val user = findPersistedUser()

        val foundUser = cateringListService.findByUserId(user!!.id)
        assert(foundUser!!.id > 0)
        assert(cateringList.id.toInt() > 0)
    }

    @Test
    @Transactional
    fun `Error when id is greater than one for create`() {
        val cateringList = buildCateringList().copy(id = 4)

        val errorList = mutableListOf<String>()
        val result = ServiceUtils.handleServiceCall({ cateringListService.create(cateringList) }, errorList)

        assert(result == null)
        assert(errorList.isNotEmpty() && errorList.size == 1)
    }

    @Test
    @Transactional
    fun `Error when userId is greater than one for create`() {
        val cateringList = buildCateringList().copy(user = buildUser())

        val errorList = mutableListOf<String>()
        val result = ServiceUtils.handleServiceCall({ cateringListService.create(cateringList) }, errorList)

        assert(result == null)
        assert(errorList.isNotEmpty() && errorList.size == 1)
    }

    @Test
    @Transactional
    fun `Error when inactive is set for create`() {
        val cateringList = buildCateringList().copy(isActive = false)

        val errorList = mutableListOf<String>()
        val result = ServiceUtils.handleServiceCall({ cateringListService.create(cateringList) }, errorList)

        assert(result == null)
        assert(errorList.isNotEmpty() && errorList.size == 1)
    }

    @Test
    @Transactional
    fun `itemList is null for create, defaults to 100`() {
        val cateringList = buildCateringList().copy(itemLimit = null)

        val errorList = mutableListOf<String>()
        val result = ServiceUtils.handleServiceCall({ cateringListService.create(cateringList) }, errorList)

        assert(result != null)
        assert(result!!.itemLimit == 100)
    }

    @Test
    @Transactional
    fun `maxUsers is null for create, defaults to 10`() {
        val cateringList = buildCateringList().copy(maxUsers = null)

        val errorList = mutableListOf<String>()
        val result = ServiceUtils.handleServiceCall({ cateringListService.create(cateringList) }, errorList)

        assert(result != null)
        assert(result!!.maxUsers == 10)
    }

    @Test
    @Transactional
    fun `sessionId is unique for create`() {
        val cateringList = buildCateringList().copy(sessionId = "Bob the builder")

        val errorList = mutableListOf<String>()
        val result = ServiceUtils.handleServiceCall({ cateringListService.create(cateringList) }, errorList)

        assert(result != null)
        assert(result!!.sessionId != "Bob the builder")
        assert(result.sessionId!!.length == 8)
    }
}
