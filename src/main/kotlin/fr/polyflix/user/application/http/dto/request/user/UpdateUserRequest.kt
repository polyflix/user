package fr.polyflix.user.application.http.dto.request.user

data class UpdateUserRequest(
    val avatar: String?,
    val username: String?,
    val lastName: String?,
    val firstName: String?
)