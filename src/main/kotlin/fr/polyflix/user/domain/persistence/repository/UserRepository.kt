package fr.polyflix.user.domain.persistence.repository

import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.domain.entity.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional
import java.util.UUID

interface UserRepository {
    fun findAll(pageable: Pageable): Page<User>
    fun findAllByIds(ids: List<UUID>): List<User>
    fun findOne(id: UUID): Optional<User>
    fun create(user: User): User
    fun update(id: UUID, userName: String?, firstName: String?, lastName: String?, avatar: String?, roles: List<Role>?): Optional<User>
    fun deleteOne(id: UUID)
}