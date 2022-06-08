package fr.polyflix.user.domain.authentication

import fr.polyflix.user.domain.enum.Roles
import java.util.UUID

data class AuthenticatedUser(val id: UUID, val roles: List<Roles>) {
    val isAdmin = roles.contains(Roles.Administrator)
}