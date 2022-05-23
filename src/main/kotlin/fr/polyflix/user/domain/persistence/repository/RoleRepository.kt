package fr.polyflix.user.domain.persistence.repository

import fr.polyflix.user.domain.entity.Role
import fr.polyflix.user.domain.enum.Roles
import java.util.Optional

interface RoleRepository {
    /**
     * Find a role by its name.
     */
    fun findByName(name: Roles): Optional<Role>
}