package com.invstore.invstorejvm.models.users

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun `should persist a user`() {
        val user = User(username = "john", name = "John Doe", password = "password", email = "john@doe.com")

        assertDoesNotThrow {
            entityManager.persist(user)
            entityManager.flush()
        }
    }
}