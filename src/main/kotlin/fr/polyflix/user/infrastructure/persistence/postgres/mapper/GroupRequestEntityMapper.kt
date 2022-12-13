package fr.polyflix.user.infrastructure.persistence.postgres.mapper

import fr.polyflix.user.domain.entity.GroupRequest
import fr.polyflix.user.infrastructure.persistence.postgres.entity.GroupRequestEntity
import org.springframework.stereotype.Component

@Component
class GroupRequestEntityMapper : PersistenceMapper<GroupRequest, GroupRequestEntity> {
    override fun toDomain(entity: GroupRequestEntity): GroupRequest {
        return GroupRequest(entity.id, entity.reason, entity.createdDate)
    }

    override fun toEntity(domain: GroupRequest): GroupRequestEntity {
        return GroupRequestEntity(domain.id, domain.reason, domain.createdDate)
    }
}