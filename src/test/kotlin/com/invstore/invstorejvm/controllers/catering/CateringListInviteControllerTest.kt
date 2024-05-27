package com.invstore.invstorejvm.controllers.catering

import com.invstore.invstorejvm.InvstoreJvmApplication
import com.invstore.invstorejvm.models.catering.*
import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.models.users.UserDTO
import com.invstore.invstorejvm.services.OperationResult
import com.invstore.invstorejvm.services.catering.CateringListInviteService
import com.invstore.invstorejvm.services.catering.CateringListService
import com.invstore.invstorejvm.services.user.UserService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDateTime


@SpringBootTest(classes = [InvstoreJvmApplication::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CateringListInviteControllerTest {
    @Autowired
    private lateinit var cateringListInviteService: CateringListInviteService

    @Autowired
    private lateinit var usrService: UserService

    @Autowired
    lateinit var cateringListService: CateringListService

    @Autowired
    private val mvc: MockMvc? = null

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

    fun buildAndCreateInviteList(sent: String, rec: String) {
        var cUsr = buildUser(sent)
        cUsr = usrService.create(cUsr)!!
        val cList = buildCateringList(cUsr)
        val list = parseRes(cateringListService.create(cList))
        val inv = buildCateringListInvite(rec, cUsr.toUserDTO(), list!!)
        cateringListInviteService.create(inv)
    }

    fun <T> parseRes(opRes: OperationResult<T>): T? {
        return when(opRes) {
            is OperationResult.Success<*> -> {
                // Assuming InviteListDTO is associated with a successful result
                // Return the InviteListDTO associated with the success
                opRes.data as T
            }
            is OperationResult.Error<*> -> {
                // Handle the error case, maybe return null or some default value
                null
            }
        }
    }

    @Test
    @Transactional
    fun getInviteList() {
        buildAndCreateInviteList("jimboBean@bean.com", "new@new.new")
        val op = cateringListInviteService.findById(2)
        val inv = parseRes(op)
        mvc!!.perform(MockMvcRequestBuilders.get("/api/v1/invitelist/2"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.value.userEmail").value(inv!!.userEmail));
    }

    @Test
    @Transactional
    fun getInviteListByReceivedEmail() {
        buildAndCreateInviteList("jimboBean@bean.com", "new@new.new")
        mvc!!.perform(MockMvcRequestBuilders.get("/api/v1/invitelist/received/new@new.new"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.value[0].userEmail").value("new@new.new"));
    }

    @Test
    @Transactional
    fun getInviteListBySentEmail() {
        buildAndCreateInviteList("jimboBean@bean.com", "new@new.new")
        val op = cateringListInviteService.findById(1)
        val inv = parseRes(op)
        mvc!!.perform(MockMvcRequestBuilders.get("/api/v1/invitelist/sent/jimboBean@bean.com"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.value[0].userEmail").value(inv!!.userEmail));
    }
}