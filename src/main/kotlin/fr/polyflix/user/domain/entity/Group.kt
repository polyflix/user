package fr.polyflix.user.domain.entity

import java.util.*

class Group(
    val id: UUID,
    var name: String,
    val slug: String,
    val owner: User,
    var members: MutableSet<User>,
    var groupRequests: MutableSet<GroupRequest>
) {
    override fun toString(): String {
        return "Group { name = $name, slug = $slug }"
    }
}