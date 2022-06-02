package fr.polyflix.user.domain.entity

import fr.polyflix.user.domain.enum.Roles
import fr.polyflix.user.domain.error.UserInvalidAttributeError
import fr.polyflix.user.domain.error.UserWithoutRoleError
import java.util.Base64
import java.util.UUID

class User private constructor(
    val id: UUID,
    val email: String,
    var firstName: String,
    var lastName: String,
    var username: String,
    var avatar: String,
    var roles: List<Role>
) {
    data class Builder(
        var id: UUID = UUID.randomUUID(),
        var email: String = "",
        var firstName: String = "",
        var lastName: String = "",
        var username: String = "",
        var avatar: String = "",
        var roles: List<Role> = listOf()
    ) {
        fun withId(id: UUID) = apply { this.id = id }
        fun withEmail(email: String) = apply { this.email = email }
        fun withFirstname(firstName: String?) = apply { this.firstName = firstName ?: "" }
        fun withLastname(lastName: String?) = apply { this.lastName = lastName ?: "" }
        fun withUsername(username: String) = apply { this.username = username }
        fun withAvatar(avatar: String) = apply { this.avatar = avatar }
        fun withRoles(roles: List<Role>) = apply { this.roles = roles }
        fun build(): User {

            // Generate default avatar if the avatar is not provided at the build
            val avatar = if(this.avatar != "") this.avatar else {
                val seed = Base64.getEncoder().encodeToString(this.email.toByteArray())
                "https://avatars.dicebear.com/api/identicon/$seed.svg"
            }

            return User(id, email, firstName, lastName, username, avatar, roles)
                .validate()
        }
    }

    fun validate(): User {
        var invalidFields = mutableListOf<String>()
        if (roles.isEmpty()) throw UserWithoutRoleError()
        if (email.isEmpty()) invalidFields.add("email")

        if (invalidFields.size > 0) throw UserInvalidAttributeError(id, invalidFields)

        return this
    }

    fun setFirstName(firstName: String) : User {
        if (firstName.isEmpty()) throw UserInvalidAttributeError(id, listOf("firstName"))
        this.firstName = firstName
        return this
    }

    fun setUsername(username: String) : User {
        if (username.isEmpty()) throw UserInvalidAttributeError(id, listOf("username"))
        this.username = username
        return this
    }

    fun setLastName(lastName: String) : User {
        if (lastName.isEmpty()) throw UserInvalidAttributeError(id, listOf("lastName"))
        this.lastName = lastName
        return this
    }

    fun setAvatar(avatar: String): User {
        this.avatar = avatar
        return this
    }

    fun setRoles(roles: List<Role>): User {
        this.roles = roles
        return this
    }

    override fun toString(): String {
        return "User { id: $id, email: $email, firstName: $firstName, lastName: $lastName }"
    }
}