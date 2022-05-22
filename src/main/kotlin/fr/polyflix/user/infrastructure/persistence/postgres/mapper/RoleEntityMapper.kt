package fr.polyflix.user.infrastructure.persistence.postgres.mapper

import fr.polyflix.user.domain.entity.Role
import fr.polyflix.user.infrastructure.persistence.postgres.entity.RoleEntity
import org.springframework.stereotype.Component

@Component
class RoleEntityMapper: PersistenceMapper<Role, RoleEntity> {
    override fun toDomain(entity: RoleEntity): Role {
        return Role(entity.id, entity.name)
    }

    override fun toEntity(role: Role): RoleEntity {
        return RoleEntity(
            role.id,
            role.name
        )
    }
}