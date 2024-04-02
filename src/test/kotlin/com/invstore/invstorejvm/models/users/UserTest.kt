package com.invstore.invstorejvm.models.users

import com.invstore.invstorejvm.InvstoreJvmApplication
import com.invstore.invstorejvm.services.isValidPositiveInt
import com.invstore.invstorejvm.services.user.UserService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [InvstoreJvmApplication::class])
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private lateinit var userService: UserService

    @Test
    @Transactional
    fun `Should persist a user`() {
        val user = User(
            username = "john",
            name = "John Doe",
            password = "password",
            email = "john@doe.com",
            isActive = true
        )

        userService.create(user)

        val foundUser = userService.findByUsername("john")
        assert(foundUser.data!!.id > 0)
    }

    @Test
    @Transactional
    fun `Persist User, but don't update date created`() {
        var user = User(
            username = "john",
            name = "John Doe",
            password = "password",
            email = "john@doe.com",
            isActive = true
        )

        user = userService.create(user)!!

        assertNotNull(user)
        assertEquals("john", user.username)
        assertEquals("John Doe", user.name)
        assertEquals("password", user.password)
        assertEquals("john@doe.com", user.email)

        // Now update something and ensure that the created_at doesn't change
        user.username = "Johnny Boy"

        val updatedUser: User? = userService.update(user)
        assertNotNull(updatedUser)
        assertEquals(user.id, updatedUser!!.id)
        assertEquals(user.createdAt, updatedUser.createdAt)
    }

    @Test
    @Transactional
    fun `Persist User, find by email`() {
        val user = User(
            username = "bbjacky",
            name = "Bo Jackson",
            password = "password",
            email = "bbjackson@nfl.com",
            isActive = true
            )

        userService.create(user)

        val foundUser = userService.findByEmail("bbjackson@nfl.com")
        assertNotNull(user)
        assertEquals("bbjackson@nfl.com", foundUser!!.email)
    }
}