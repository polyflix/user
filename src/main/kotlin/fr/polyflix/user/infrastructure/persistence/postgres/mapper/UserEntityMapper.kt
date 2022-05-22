package fr.polyflix.user.infrastructure.persistence.postgres.mapper

import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.infrastructure.persistence.postgres.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserEntityMapper(private val roleMapper: RoleEntityMapper): PersistenceMapper<User, UserEntity> {
    override fun toDomain(entity: UserEntity): User {
        return User.Builder()
            .withId(entity.id)
            .withEmail(entity.email)
            .withFirstname(entity.firstName)
            .withLastname(entity.lastName)
            .withUsername(entity.username)
            .withAvatar(entity.avatar)
            .withRoles(entity.roles.map { roleMapper.toDomain(it) })
            .build()
    }

    override fun toEntity(user: User): UserEntity {
        return UserEntity(
            user.id,
            user.email,
            user.firstName,
            user.lastName,
            user.username,
            user.avatar,
            user.roles.map { roleMapper.toEntity(it) }
        )
    }
}