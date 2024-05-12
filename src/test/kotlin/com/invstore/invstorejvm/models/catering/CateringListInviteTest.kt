package com.invstore.invstorejvm.models.catering

import com.invstore.invstorejvm.InvstoreJvmApplication
import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.models.users.UserDTO
import com.invstore.invstorejvm.services.OperationResult
import com.invstore.invstorejvm.services.ServiceUtils
import com.invstore.invstorejvm.services.catering.CateringListInviteService
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
class CateringListInviteTest {
    @Autowired
    private lateinit var cateringListService: CateringListService
    @Autowired
    private lateinit var cateringListInviteService: CateringListInviteService

    @Autowired
    private lateinit var userService: UserService

    fun buildCateringList(user: User): CateringListCreateDTO {
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

    fun buildUser(email: String = "ral@self.com"): User {
        return User(
            id = 0,
            name = "Ralph",
            username = "raphie",
            isActive = true,
            email = email,
            password = null,
            timezone = "UTC",
            lastLoggedIn = LocalDateTime.now(),
            provider = null,
            providerId = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun buildCateringListInvite(
        user: String = "ral@self.com",
        who: UserDTO,
        list: CateringListDTO
        ): InviteListCreateDTO {
        return InviteListCreateDTO(
            id = 0,
            userEmail = user,
            whoInvited = who,
            relatedList = list,
        )
    }

    fun findPersistedUser(): User? {
        return userService.findByUsername("raphie").data!!.toUser()
    }

    @Test
    @Transactional
    fun `Should persist a cateringListInvite`() {
        var user: User = buildUser()
        var user2: User = buildUser("otherguy@thing.com")

        user = userService.create(user)!!
        user2 = userService.create(user2)!!

        val cateringList = buildCateringList(user)
        cateringListService.create(cateringList)

        var list: CateringListDTO? = null
        when (val cateringLists = cateringListService.findByUserId(user.id)) {
            is OperationResult.Success -> {
                assert(cateringLists.data!!.isNotEmpty())
                list = cateringLists.data!!.first()
            }

            is OperationResult.Error -> fail()
        }

        val inv = buildCateringListInvite(user= user2.email, who = user.toUserDTO(), list = list!!)
        when (val createdInvite = cateringListInviteService.create(inv)) {
            is OperationResult.Success -> {
                assert(createdInvite.data!!.id > 0)
            }

            is OperationResult.Error -> fail()
        }

        when (val l = cateringListInviteService.findById(1)) {
            is OperationResult.Success -> {
                assert(l.data != null)
            }
            is OperationResult.Error -> fail()
        }
    }
}
