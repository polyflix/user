package fr.polyflix.user.domain.entity

import java.time.LocalDateTime
import java.util.*

class GroupRequest(
    val id: UUID,
    val reason: String,
    var createdDate: LocalDateTime
)