package fr.polyflix.user.domain.messaging.model

import java.util.UUID

data class UserMessage(
    val id: UUID,
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)