package fr.polyflix.user.application.http.dto.request.user

import fr.polyflix.user.domain.enum.Roles

data class UpdateUserRequest(
    val avatar: String?,
    val username: String?,
    val lastName: String?,
    val firstName: String?,
    val roles: List<Roles>?,
)