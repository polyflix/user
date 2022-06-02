package fr.polyflix.user.infrastructure.authentication.keycloak.model

import java.util.UUID

data class KeycloakUser(
    val id: UUID,
    val email: String?,
    val username: String,
    val firstName: String?,
    val lastName: String?
)