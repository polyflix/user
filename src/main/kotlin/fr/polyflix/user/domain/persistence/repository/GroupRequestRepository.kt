package fr.polyflix.user.domain.persistence.repository

import fr.polyflix.user.domain.entity.GroupRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface GroupRequestRepository {
    fun findAll(pageable: Pageable): Page<GroupRequest>
    fun findOne(id: UUID): Optional<GroupRequest>
}