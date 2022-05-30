package fr.polyflix.user.domain.messaging.model

import fr.polyflix.user.domain.entity.Role
import fr.polyflix.user.domain.enum.Roles
import java.util.UUID

data class UserMessage(
    val id: UUID? = null,
    val email: String? = null,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val avatar: String? = null,
    val roles: List<Roles>? = null
)