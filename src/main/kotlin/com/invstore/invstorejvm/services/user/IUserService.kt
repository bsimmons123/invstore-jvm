package com.invstore.invstorejvm.services.user

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.models.users.UserDTO
import com.invstore.invstorejvm.services.OperationResult

interface IUserService {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): OperationResult.Success<UserDTO?>
    fun findByProviderId(id: String): User?
    fun create(user: User): User?
    fun update(user: User): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByEmailOrUsername(email: String, username: String): User?
}