package fr.polyflix.user.infrastructure.persistence.postgres.impl

import fr.polyflix.user.domain.entity.Group
import fr.polyflix.user.domain.persistence.repository.GroupRepository
import fr.polyflix.user.infrastructure.persistence.postgres.SpringGroupRepository
import fr.polyflix.user.infrastructure.persistence.postgres.mapper.GroupEntityMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupRepositoryImpl(private val repository: SpringGroupRepository, private val mapper: GroupEntityMapper): GroupRepository {
    override fun getAllGroupsWithPagination(pageable: Pageable): Page<Group> {
        val entities = repository.findAll(pageable)
        return entities.map { mapper.toDomain(it) }
    }

    override fun getById(id: UUID): Optional<Group> {
        val entity = repository.findById(id)
        return entity.map { mapper.toDomain(it) }
    }

    override fun getBySlug(slug: String): Optional<Group> {
        val entity = repository.findBySlug(slug)
        return entity.map { mapper.toDomain(it) }
    }

    override fun save(group: Group): Group {
        val saved = repository.save(mapper.toEntity(group))
        return mapper.toDomain(saved)
    }

    override fun delete(group: Group): Group {
        repository.delete(mapper.toEntity(group))
        return group
    }
}