package fr.polyflix.user.domain.props

import java.util.UUID

data class UpdateGroupProps(
    val name: String?,
    val members: List<UUID>?
)