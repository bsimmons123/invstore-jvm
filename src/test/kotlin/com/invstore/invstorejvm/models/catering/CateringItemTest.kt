package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.InvstoreJvmApplication
import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.repositories.catering.CateringItemTypeRepository
import com.invstore.invstorejvm.repositories.catering.CateringListRepository
import com.invstore.invstorejvm.services.OperationResult
import com.invstore.invstorejvm.services.catering.CateringItemService
import com.invstore.invstorejvm.services.catering.CateringItemTypeService
import com.invstore.invstorejvm.services.catering.CateringListService
import com.invstore.invstorejvm.services.user.UserService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@SpringBootTest(classes = [InvstoreJvmApplication::class])
@ActiveProfiles("test")
class CateringItemTest {
    @Autowired
    private lateinit var cateringListRepository: CateringListRepository

    @Autowired
    private lateinit var cateringItemTypeRepo: CateringItemTypeRepository

    @Autowired
    private lateinit var cateringItemService: CateringItemService

    @Autowired
    private lateinit var userService: UserService

    fun buildCateringItem(): CateringItemCreateDTO {
        val createdList = cateringListRepository.save(buildCateringList())
        val createdType = cateringItemTypeRepo.save(buildCateringType(createdList))

        return CateringItemCreateDTO(
            label = "Apple bottom jeans",
            description = "Spaghettios",
            listId = createdList.id,
            quantity = 10,
            status = CateringItem.Status.PENDING,
            type = createdType
        )
    }

    fun buildCateringItemNoType(): CateringItemCreateDTO {
        val createdList = cateringListRepository.save(buildCateringList())

        return CateringItemCreateDTO(
            label = "Apple bottom jeans",
            description = "Spaghettios",
            listId = createdList.id,
            quantity = 10,
            status = CateringItem.Status.PENDING,
            type = CateringItemType()
        )
    }

    fun buildCateringType(list: CateringList): CateringItemType {
        return CateringItemType(
            id = 0,
            label = "This be a type",
            description = "This isnt a description",
            list = list
        )
    }

    fun buildCateringList(): CateringList {
        var user: User = buildUser()

        user = userService.create(user)!!

        return CateringList(
            description = "Stinky items only",
            label = "Stink pot",
            isActive = true,
            itemLimit = 10,
            location = "Asgard",
            maxUsers = 2,
            user = user,
            visibility = CateringList.Visibility.PUBLIC,
            notes = null,
            id = 0,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            sessionId = "EHWHOKNOWS"
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

    @Test
    @Transactional
    fun `Should persist a cateringItem`() {
        val cateringItem = buildCateringItem()

        when(val createdItem = cateringItemService.create(cateringItem)) {
            is OperationResult.Success -> {
                assert(createdItem.data!!.id.toInt() > 0)
                assert(createdItem.data!!.listId.toInt() > 0)
            }

            is OperationResult.Error -> fail()
        }
    }

    @Test
    @Transactional
    fun `Error when description is null for create`() {
        val cateringList = buildCateringItem().copy(description = "")

        when(val result = cateringItemService.create(cateringList)) {
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
    fun `Error when type is null for create`() {
        val cateringList = buildCateringItemNoType()

        when(val result = cateringItemService.create(cateringList)) {
            is OperationResult.Error -> {
                assert(result.errors.isNotEmpty())
            }
            is OperationResult.Success -> {
                fail()
            }
        }
    }
}
