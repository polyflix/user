package fr.polyflix.user.application.http.dto.response.group

import fr.polyflix.user.application.http.dto.response.groupRequest.GroupRequestResponse
import fr.polyflix.user.application.http.dto.response.user.UserResponse
import fr.polyflix.user.domain.entity.Group
import java.util.*

class GroupResponse(group: Group) {
    val id: UUID = group.id
    val name: String = group.name
    val slug: String = group.slug
    val owner: UserResponse = UserResponse(group.owner)
    val members: List<UserResponse> = group.members.map { UserResponse(it) }
    val groupRequests: List<GroupRequestResponse> = group.groupRequests.map { GroupRequestResponse(it) }
}