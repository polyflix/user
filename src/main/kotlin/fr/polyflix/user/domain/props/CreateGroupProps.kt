package fr.polyflix.user.domain.props

import java.util.UUID

data class CreateGroupProps(
    val name: String,
    val owner: UUID,
    val members: List<UUID>
)