package fr.polyflix.user.application.http.dto.response.group

import fr.polyflix.user.domain.entity.Group
import org.springframework.data.domain.Page

class PaginatedGroupResponse(data: Page<Group>) {
    val totalElements: Long = data.totalElements
    val totalPages: Int = data.totalPages
    val currentPage: Int = data.number
    val data: List<GroupResponse> = data.content.map { GroupResponse(it) }
}