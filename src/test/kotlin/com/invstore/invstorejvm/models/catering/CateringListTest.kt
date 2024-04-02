package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.InvstoreJvmApplication
import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.services.OperationResult
import com.invstore.invstorejvm.services.ServiceUtils
import com.invstore.invstorejvm.services.catering.CateringListService
import com.invstore.invstorejvm.services.isValidPositiveInt
import com.invstore.invstorejvm.services.user.UserService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
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

    fun buildCateringList(): CateringListCreateDTO {
        var user: User = buildUser()

        user = userService.create(user)!!

        return CateringListCreateDTO(
            description = "Stinky items only",
            label = "Stink pot",
            isActive = true,
            itemLimit = 10,
            location = "Asgard",
            maxUsers = 2,
            user = user.toUserDTO(),
            visibility = CateringList.Visibility.PUBLIC,
            notes = null
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
        return userService.findByUsername("raphie").data!!.toUser()
    }

    @Test
    @Transactional
    fun `Should persist a cateringList`() {
        val cateringList = buildCateringList()

        val createdList = cateringListService.create(cateringList)

        val user = findPersistedUser()

        when(createdList) {
            is OperationResult.Success -> {
                assert(createdList.data!!.id.toInt() > 0)

            }

            is OperationResult.Error -> fail()
        }

        when (val cateringLists = cateringListService.findByUserId(user!!.id)) {
            is OperationResult.Success -> {
                assert(cateringLists.data!!.isNotEmpty())
            }

            is OperationResult.Error -> fail()
        }
    }

    @Test
    @Transactional
    fun `Error when inactive is set for create`() {
        val cateringList = buildCateringList().copy(isActive = false)

        val result = cateringListService.create(cateringList)

        when(result) {
            is OperationResult.Error -> {
                assert(result.errors.isNotEmpty())
            }
            is OperationResult.Success -> {
                fail()
            }
        }
    }

    @Test
    @Transactional
    fun `itemList is null for create, defaults to 100`() {
        val cateringList = buildCateringList().copy(itemLimit = null)

        val result = cateringListService.create(cateringList)

        when(result) {
            is OperationResult.Error -> {
                fail()
            }
            is OperationResult.Success -> {
                assert(result.data!!.itemLimit == 100)
            }
        }
    }

    @Test
    @Transactional
    fun `maxUsers is null for create, defaults to 10`() {
        val cateringList = buildCateringList().copy(maxUsers = null)

        when(val result = cateringListService.create(cateringList)) {
            is OperationResult.Error -> {
                fail()
            }
            is OperationResult.Success -> {
                assert(result.data!!.maxUsers == 10)
            }
        }
    }

    @Test
    @Transactional
    fun `sessionId is unique for create`() {
        val cateringList = buildCateringList()

        when(val result = cateringListService.create(cateringList)) {
            is OperationResult.Error -> {
                fail()
            }
            is OperationResult.Success -> {
                assert(result.data != null)
                assert(result.data!!.sessionId!!.length == 8)
            }
        }
    }
}
