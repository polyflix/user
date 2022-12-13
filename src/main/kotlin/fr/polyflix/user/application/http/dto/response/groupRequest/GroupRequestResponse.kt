package fr.polyflix.user.application.http.dto.response.groupRequest

import fr.polyflix.user.domain.entity.GroupRequest
import java.time.LocalDateTime
import java.util.*

class GroupRequestResponse(groupRequest: GroupRequest) {
    val id: UUID = groupRequest.id
    val reason: String = groupRequest.reason
    val createdDate: LocalDateTime = groupRequest.createdDate
}