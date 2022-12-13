package fr.polyflix.user.infrastructure.persistence.postgres.mapper

import fr.polyflix.user.domain.entity.Group
import fr.polyflix.user.infrastructure.persistence.postgres.entity.GroupEntity
import org.springframework.stereotype.Component

@Component
class GroupEntityMapper(
    private val userMapper: UserEntityMapper,
    private val groupRequestMapper: GroupRequestEntityMapper
) : PersistenceMapper<Group, GroupEntity> {
    override fun toDomain(entity: GroupEntity): Group {
        return Group(
            entity.id,
            entity.name,
            entity.slug,
            userMapper.toDomain(entity.owner),
            entity.members.map { userMapper.toDomain(it) }.toMutableSet(),
            entity.groupRequests.map { groupRequestMapper.toDomain(it) }.toMutableSet()
        )
    }

    override fun toEntity(domain: Group): GroupEntity {
        return GroupEntity(
            domain.id,
            domain.name,
            domain.slug,
            userMapper.toEntity(domain.owner),
            domain.members.map { userMapper.toEntity(it) }.toSet(),
            domain.groupRequests.map { groupRequestMapper.toEntity(it) }.toSet()
        )
    }
}