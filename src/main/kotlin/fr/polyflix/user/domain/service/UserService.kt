package fr.polyflix.user.domain.service

import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.domain.enum.Roles
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional
import java.util.UUID

interface UserService {
    fun getUsers(pageable: Pageable): Page<User>
    fun createUser(user: User): User
    fun findUserById(id: UUID): Optional<User>
    fun updateUser(id: UUID, userName: String?, firstName: String?, lastName: String?, avatar: String?, roles: List<Roles>?): Optional<User>
    fun deleteUser(id: UUID)
    fun findUsersById(ids: List<UUID>): List<User>
}