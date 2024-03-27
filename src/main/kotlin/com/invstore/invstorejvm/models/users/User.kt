package com.invstore.invstorejvm.models.users
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "app_user")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 100)
    var username: String = "",

    @Column(nullable = false, length = 50)
    var name: String = "",

    @Column(nullable = false, length = 2000)
    var password: String = "",

    @Column(unique = true, nullable = false, length = 120)
    var email: String = "",

    var provider: String? = null,

    var providerId: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    var roles: Set<Role> = emptySet(),

    @Column(name = "last_logged_in")
    var lastLoggedIn: LocalDateTime = LocalDateTime.now(),

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_active")
    var isActive: Boolean = true,

    @Column(length = 50)
    var timezone: String = "UTC"
)