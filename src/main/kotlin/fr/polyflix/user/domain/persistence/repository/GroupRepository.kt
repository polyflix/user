package fr.polyflix.user.domain.persistence.repository

import fr.polyflix.user.domain.entity.Group
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional
import java.util.UUID

interface GroupRepository {
    fun getAllGroupsWithPagination(pageable: Pageable): Page<Group>
    fun getById(id: UUID): Optional<Group>
    fun getBySlug(slug: String): Optional<Group>
    fun save(group: Group): Group
    fun delete(group: Group): Group
}