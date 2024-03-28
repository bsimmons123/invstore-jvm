package com.invstore.invstorejvm.models.users

import jakarta.persistence.*


@Entity
class Role(@Column(nullable = false, unique = true) var name: String?) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Role [id=").append(id).append(", name=").append(name).append("]")
        return builder.toString()
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = (prime * result) + (if ((id == null)) 0 else id.hashCode())
        result = (prime * result) + (if ((name == null)) 0 else name.hashCode())
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val other1 = other as Role
        if (id == null) {
            if (other1.id != null) {
                return false
            }
        } else if (id != other1.id) {
            return false
        }
        if (name == null) {
            if (other1.name != null) {
                return false
            }
        } else if (name != other1.name) {
            return false
        }
        return true
    }
}