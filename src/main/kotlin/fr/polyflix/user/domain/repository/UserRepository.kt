package fr.polyflix.user.domain.repository

import fr.polyflix.user.domain.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional
import java.util.UUID

interface UserRepository {
    fun findAll(pageable: Pageable): Page<User>
    fun findOne(id: UUID): Optional<User>
    fun create(user: User): User
    fun update(id: UUID, userName: String?, firstName: String?, lastName: String?, avatar: String?): Optional<User>
    fun deleteOne(id: UUID)
}