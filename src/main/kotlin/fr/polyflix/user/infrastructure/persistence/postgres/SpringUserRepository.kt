package fr.polyflix.user.infrastructure.persistence.postgres

import fr.polyflix.user.infrastructure.persistence.postgres.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SpringUserRepository: JpaRepository<UserEntity, UUID>