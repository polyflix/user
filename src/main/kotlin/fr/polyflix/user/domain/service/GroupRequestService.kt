package fr.polyflix.user.domain.service

import fr.polyflix.user.domain.entity.GroupRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface GroupRequestService {
    fun getGroupRequests(pageable: Pageable): Page<GroupRequest>
    fun findGroupRequestById(id: UUID): Optional<GroupRequest>
}