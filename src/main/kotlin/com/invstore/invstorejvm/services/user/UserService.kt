package com.invstore.invstorejvm.services.user

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.repositories.users.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(private var repository: UserRepository) : IUserService {

    override fun findByEmail(email: String): com.invstore.invstorejvm.models.users.User? {
        return repository.findByEmail(email)
    }

    override fun findByUsername(username: String): User? {
        return repository.findByUsername(username)
    }

    override fun findByProviderId(id: String): User? {
        return repository.findByProviderId(id)
    }

    override fun save(user: User): User? {
        // Update the updated at value
        user.updatedAt = LocalDateTime.now()

        return repository.save(user)
    }

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
