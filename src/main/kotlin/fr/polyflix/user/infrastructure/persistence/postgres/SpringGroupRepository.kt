package fr.polyflix.user.infrastructure.persistence.postgres

import fr.polyflix.user.infrastructure.persistence.postgres.entity.GroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface SpringGroupRepository: JpaRepository<GroupEntity, UUID> {
    fun findBySlug(slug: String): Optional<GroupEntity>
    fun findAllByName(name: String): List<GroupEntity>
}