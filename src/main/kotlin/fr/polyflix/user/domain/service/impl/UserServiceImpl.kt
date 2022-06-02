package fr.polyflix.user.domain.service.impl

import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.domain.enum.Roles
import fr.polyflix.user.domain.messaging.event.Trigger
import fr.polyflix.user.domain.messaging.event.UserEvent
import fr.polyflix.user.domain.messaging.producer.UserProducer
import fr.polyflix.user.domain.persistence.repository.RoleRepository
import fr.polyflix.user.domain.persistence.repository.UserRepository
import fr.polyflix.user.domain.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val userProducer: UserProducer
) : UserService {
    override fun getUsers(pageable: Pageable): Page<User> {
        return userRepository.findAll(pageable)
    }

    override fun findUserById(id: UUID): Optional<User> {
        return userRepository.findOne(id)
    }

    override fun createUser(user: User): User {
        val saved = userRepository.create(user)
        val event = UserEvent.Builder()
            .withTrigger(Trigger.CREATE)
            .withUser(saved)
            .build()

        // Send event to the messaging system
        userProducer.send(event)

        return saved
    }

    override fun updateUser(
        id: UUID,
        userName: String?,
        firstName: String?,
        lastName: String?,
        avatar: String?,
        roles: List<Roles>?
    ): Optional<User> {
        // Get the new roles if the roles are updated
        val roles = roles?.map { roleRepository.findByName(it).get() }
        val updated = userRepository.update(id, userName, firstName, lastName, avatar, roles)
        val event = UserEvent.Builder()
            .withUser(updated.get())
            .withTrigger(Trigger.UPDATE)
            .build()

        // Send update event to the messaging system
        userProducer.send(event)

        return updated
    }

    override fun deleteUser(id: UUID) {
        userRepository.deleteOne(id)
        val event = UserEvent.Builder()
            .withId(id)
            .withTrigger(Trigger.DELETE)
            .build()

        // Send delete event to the messaging system
        userProducer.send(event)
    }
}