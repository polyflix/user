package fr.polyflix.user.infrastructure.persistence.postgres

import fr.polyflix.user.domain.enum.Roles
import fr.polyflix.user.infrastructure.persistence.postgres.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SpringRoleRepository: JpaRepository<RoleEntity, UUID> {
    fun findByName(name: Roles): Optional<RoleEntity>
}