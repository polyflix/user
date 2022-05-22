package fr.polyflix.user.infrastructure.persistence.postgres.impl

import fr.polyflix.user.domain.entity.Role
import fr.polyflix.user.domain.enum.Roles
import fr.polyflix.user.domain.repository.RoleRepository
import fr.polyflix.user.infrastructure.persistence.postgres.SpringRoleRepository
import fr.polyflix.user.infrastructure.persistence.postgres.mapper.RoleEntityMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class RoleRepositoryImpl(private val repository: SpringRoleRepository, private val mapper: RoleEntityMapper) :
    RoleRepository {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun findByName(name: Roles): Optional<Role> {
        return repository
            .findByName(name)
            .map { mapper.toDomain(it) }
            .or {
                logger.error("No role found in database name $name")
                Optional.empty()
            }
    }
}