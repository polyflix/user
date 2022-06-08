package fr.polyflix.user.domain.entity

import java.util.UUID

class Group(
    val id: UUID,
    var name: String,
    val slug: String,
    val owner: User,
    var members: MutableSet<User>
) {
    override fun toString(): String {
        return "Group { name = $name, slug = $slug }"
    }
}