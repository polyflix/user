package fr.polyflix.user.application.http.dto.response.user

import fr.polyflix.user.domain.entity.User
import java.util.UUID

class UserResponse(user: User) {
    val id: UUID = user.id
    val email: String = user.email
    val username: String = user.username
    val firstName: String = user.firstName
    val lastName: String = user.lastName
    val avatar: String = user.avatar
    val roles: List<String> = user.roles.map { it.name.toString().uppercase() }
}