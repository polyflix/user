package fr.polyflix.user.infrastructure.persistence.postgres.impl

import fr.polyflix.user.domain.entity.Role
import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.domain.persistence.repository.UserRepository
import fr.polyflix.user.infrastructure.persistence.postgres.SpringUserRepository
import fr.polyflix.user.infrastructure.persistence.postgres.mapper.UserEntityMapper
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserRepositoryImpl(private val repository: SpringUserRepository, private val mapper: UserEntityMapper):
    UserRepository {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun findAll(pageable: Pageable): Page<User> {
        val entities = repository.findAll(pageable)
        return entities.map { mapper.toDomain(it) }
    }

    override fun findAllByIds(ids: List<UUID>): List<User> {
        return repository.findAllById(ids).map { mapper.toDomain(it) }
    }

    override fun findOne(id: UUID): Optional<User> {
        return repository
            .findById(id)
            .map { mapper.toDomain(it) }
            .or {
                logger.error("No user found in database with id $id")
                Optional.empty()
            }
    }

    override fun create(user: User): User {
        logger.info("Saving new user in database: $user")
        val createdUser = repository.save(mapper.toEntity(user))
        return mapper.toDomain(createdUser)
    }

    override fun deleteOne(id: UUID) {
        repository.deleteById(id)
        logger.info("Successfully delete user with id $id")
    }

    override fun update(
        id: UUID,
        userName: String?,
        firstName: String?,
        lastName: String?,
        avatar: String?,
        roles: List<Role>?
    ): Optional<User> {
        return findOne(id).flatMap {
            if (userName != null) it.setUsername(userName)
            if (firstName != null) it.setFirstName(firstName)
            if (lastName != null) it.setLastName(lastName)
            if (avatar != null) it.setAvatar(avatar)
            if (roles != null) it.setRoles(roles)
            
            it.validate()

            val updated = repository.save(mapper.toEntity(it))

            logger.info("Successfully updated user $id")

            Optional.of(mapper.toDomain(updated))
        }
    }
}