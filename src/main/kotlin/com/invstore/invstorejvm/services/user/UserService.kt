package com.invstore.invstorejvm.services.user

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.repositories.users.UserRepository
import com.invstore.invstorejvm.services.isValidNonPositiveInt
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(private var repository: UserRepository) : IUserService {

    override fun findByEmail(email: String): User? {
        return repository.findByEmail(email)
    }

    override fun findByUsername(username: String): User? {
        return repository.findByUsername(username)
    }

    override fun findByProviderId(id: String): User? {
        return repository.findByProviderId(id)
    }

    override fun create(user: User): User? {
        val errors = mutableListOf<String>()

        // Validate user ID
        if (user.id.toInt() != 0) {
            errors.add("Invalid user ID. Should be empty")
        }

        // Validate user name
        // Change the conditions according to your requirements
        if (user.username.isBlank()) {
            errors.add("Invalid user name. It should not be empty.")
        }

        // Validate user email
        // Simple email format check, can add more complex validation
        if (!user.email.matches("^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$".toRegex())) {
            errors.add("Invalid email format.")
        }

        if (errors.isNotEmpty()) {
            throw IllegalArgumentException(errors.joinToString())
        }

        // Update the 'updatedAt' value
        user.updatedAt = LocalDateTime.now()

        return repository.save(user)
    }

    override fun update(user: User): User? {
        val errors = mutableListOf<String>()

        // Validate user ID
        if (user.id.toInt() <= 0) {
            errors.add("Invalid user ID. Should be greater than 0")
        }

        // Validate user name
        // Change the conditions according to your requirements
        if (user.username.isBlank()) {
            errors.add("Invalid user name. It should not be empty.")
        }

        // Validate user email
        // Simple email format check, can add more complex validation
        if (!user.email.matches("^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$".toRegex())) {
            errors.add("Invalid email format.")
        }

        if (errors.isNotEmpty()) {
            throw IllegalArgumentException(errors.joinToString())
        }

        // Update the 'updatedAt' value
        user.updatedAt = LocalDateTime.now()

        return repository.save(user)    }

    override fun existsByUsername(username: String): Boolean {
        return repository.existsByUsername(username)
    }

    override fun existsByEmail(email: String): Boolean {
        return repository.existsByEmail(email)
    }

    override fun findByEmailOrUsername(email: String, username: String): User? {
        return repository.findByEmailOrUsername(email, username)
    }
}
