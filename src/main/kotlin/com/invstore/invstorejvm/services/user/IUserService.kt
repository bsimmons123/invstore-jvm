package com.invstore.invstorejvm.services.user

import com.invstore.invstorejvm.models.users.User

interface IUserService {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
    fun findByProviderId(id: String): User?
    fun save(user: User): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByEmailOrUsername(email: String, username: String): User?
}