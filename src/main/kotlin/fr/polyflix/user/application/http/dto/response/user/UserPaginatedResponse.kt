package fr.polyflix.user.application.http.dto.response.user

import fr.polyflix.user.domain.entity.User
import org.springframework.data.domain.Page

data class UserPaginatedResponse(val totalElements: Long, val totalPages: Int, val currentPage: Int, val data: List<UserResponse>){
    constructor(data: Page<User>): this(data.totalElements, data.totalPages, data.number + 1, data.content.map { UserResponse(it) })
}