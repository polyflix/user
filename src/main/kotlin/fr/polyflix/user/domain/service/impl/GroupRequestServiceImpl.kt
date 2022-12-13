package fr.polyflix.user.domain.service.impl

import fr.polyflix.user.domain.entity.GroupRequest
import fr.polyflix.user.domain.persistence.repository.GroupRequestRepository
import fr.polyflix.user.domain.service.GroupRequestService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

class GroupRequestServiceImpl(
    private val groupRequestRepository: GroupRequestRepository,
) : GroupRequestService {
    override fun getGroupRequests(pageable: Pageable): Page<GroupRequest> {
        return groupRequestRepository.findAll(pageable)
    }

    override fun findGroupRequestById(id: UUID): Optional<GroupRequest> {
        return groupRequestRepository.findOne(id)
    }
}