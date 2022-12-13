package fr.polyflix.user.application.http.dto.response.groupRequest

import fr.polyflix.user.domain.entity.GroupRequest
import org.springframework.data.domain.Page

class PaginatedGroupRequestResponse(data: Page<GroupRequest>) {
    val totalElements: Long = data.totalElements
    val totalPages: Int = data.totalPages
    val currentPage: Int = data.number
    val data: List<GroupRequestResponse> = data.content.map { GroupRequestResponse(it) }
}